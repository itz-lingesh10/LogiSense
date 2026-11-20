package com.logisense;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
@Transactional
class DatabaseMigrationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void flywayHistoryContainsVersion1WithSuccess() {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT count(*) FROM flyway_schema_history WHERE version = '1' AND success = true",
                Integer.class
        );
        assertThat(count).isEqualTo(1);
    }

    @Test
    void usersTableExistsWithExpectedColumns() {
        List<String> columnNames = jdbcTemplate.query(
                "SELECT column_name FROM information_schema.columns WHERE table_name = 'users' ORDER BY ordinal_position",
                (rs, rowNum) -> rs.getString("column_name")
        );

        assertThat(columnNames).containsExactly(
                "id",
                "email",
                "password_hash",
                "full_name",
                "phone",
                "role",
                "enabled",
                "created_at",
                "updated_at"
        );
    }

    @Test
    void insertingTwoUsersDifferingOnlyByEmailCaseFailsWithUniqueViolation() {
        jdbcTemplate.update(
                "INSERT INTO users (email, password_hash, full_name, role) VALUES (?, ?, ?, ?)",
                "alice@example.com", "hash1", "Alice Smith", "CUSTOMER"
        );

        assertThatThrownBy(() -> jdbcTemplate.update(
                "INSERT INTO users (email, password_hash, full_name, role) VALUES (?, ?, ?, ?)",
                "ALICE@example.com", "hash2", "Alice Clone", "CUSTOMER"
        )).isInstanceOf(DuplicateKeyException.class);
    }

    @Test
    void insertingUserWithInvalidRoleFailsCheckConstraint() {
        assertThatThrownBy(() -> jdbcTemplate.update(
                "INSERT INTO users (email, password_hash, full_name, role) VALUES (?, ?, ?, ?)",
                "manager@example.com", "hash1", "Manager Bob", "MANAGER"
        )).isInstanceOf(DataAccessException.class);
    }

    @Test
    void insertingValidUserSucceedsAndCreatedAtIsSet() {
        jdbcTemplate.update(
                "INSERT INTO users (email, password_hash, full_name, role) VALUES (?, ?, ?, ?)",
                "bob@example.com", "hash123", "Bob Jones", "DRIVER"
        );

        Map<String, Object> user = jdbcTemplate.queryForMap(
                "SELECT email, full_name, role, enabled, created_at, updated_at FROM users WHERE email = ?",
                "bob@example.com"
        );

        assertThat(user.get("email")).isEqualTo("bob@example.com");
        assertThat(user.get("full_name")).isEqualTo("Bob Jones");
        assertThat(user.get("role")).isEqualTo("DRIVER");
        assertThat(user.get("enabled")).isEqualTo(true);
        assertThat(user.get("created_at")).isNotNull().isInstanceOf(Timestamp.class);
        assertThat(user.get("updated_at")).isNotNull().isInstanceOf(Timestamp.class);
    }
}
