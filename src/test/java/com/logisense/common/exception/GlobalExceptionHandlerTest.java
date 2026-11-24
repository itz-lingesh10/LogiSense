package com.logisense.common.exception;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import({GlobalExceptionHandlerTest.TestController.class, GlobalExceptionHandler.class})
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void resourceNotFoundReturns404WithStandardFormat() throws Exception {
        mockMvc.perform(get("/test/not-found"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Resource 123 not found"))
                .andExpect(jsonPath("$.path").value("/test/not-found"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.fieldErrors").value(empty()));
    }

    @Test
    void conflictReturns409WithStandardFormat() throws Exception {
        mockMvc.perform(get("/test/conflict"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.error").value("Conflict"))
                .andExpect(jsonPath("$.message").value("Email already registered"))
                .andExpect(jsonPath("$.path").value("/test/conflict"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    void businessRuleReturns422WithStandardFormat() throws Exception {
        mockMvc.perform(get("/test/business-rule"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.status").value(422))
                .andExpect(jsonPath("$.error").value("Unprocessable Entity"))
                .andExpect(jsonPath("$.message").value("Package weight exceeds maximum vehicle capacity"))
                .andExpect(jsonPath("$.path").value("/test/business-rule"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    void validationFailureReturns400WithFieldErrors() throws Exception {
        String invalidBody = "{\"name\":\"\"}";

        mockMvc.perform(post("/test/validation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.path").value("/test/validation"))
                .andExpect(jsonPath("$.fieldErrors", hasSize(1)))
                .andExpect(jsonPath("$.fieldErrors[0].field").value("name"))
                .andExpect(jsonPath("$.fieldErrors[0].message").value("must not be blank"));
    }

    @Test
    void typeMismatchReturns400WithStandardFormat() throws Exception {
        mockMvc.perform(get("/test/type-mismatch/invalid-number"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message", containsString("id")))
                .andExpect(jsonPath("$.path").value("/test/type-mismatch/invalid-number"));
    }

    @Test
    void malformedJsonReturns400WithoutInternalParserLeak() throws Exception {
        String malformedJson = "{ invalid-json: true ";

        mockMvc.perform(post("/test/validation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(malformedJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Malformed JSON request body"))
                .andExpect(jsonPath("$.path").value("/test/validation"));
    }

    @Test
    void methodNotSupportedReturns405WithStandardFormat() throws Exception {
        mockMvc.perform(delete("/test/not-found"))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath("$.status").value(405))
                .andExpect(jsonPath("$.error").value("Method Not Allowed"))
                .andExpect(jsonPath("$.path").value("/test/not-found"));
    }

    @Test
    void unknownUrlReturns404WithStandardFormat() throws Exception {
        mockMvc.perform(get("/test/unknown-endpoint"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.path").value("/test/unknown-endpoint"));
    }

    @Test
    void unexpectedExceptionReturns500WithoutLeakingInternalsOrStackTrace() throws Exception {
        mockMvc.perform(get("/test/runtime-error"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.error").value("Internal Server Error"))
                .andExpect(jsonPath("$.message").value("Unexpected error"))
                .andExpect(jsonPath("$.path").value("/test/runtime-error"))
                // Ensure internal sensitive details and stack trace lines are never exposed
                .andExpect(jsonPath("$.message", not(containsString("Sensitive database password"))))
                .andExpect(jsonPath("$.message", not(containsString("at com.logisense"))));
    }

    // Isolated test controller used specifically for testing exception mappings
    @RestController
    @RequestMapping("/test")
    public static class TestController {

        @GetMapping("/not-found")
        void notFound() {
            throw new ResourceNotFoundException("Resource 123 not found");
        }

        @GetMapping("/conflict")
        void conflict() {
            throw new ConflictException("Email already registered");
        }

        @GetMapping("/business-rule")
        void businessRule() {
            throw new BusinessRuleException("Package weight exceeds maximum vehicle capacity");
        }

        @PostMapping("/validation")
        void validation(@Valid @RequestBody SampleRequest request) {
        }

        @GetMapping("/type-mismatch/{id}")
        void typeMismatch(@PathVariable Long id) {
        }

        @GetMapping("/runtime-error")
        void runtimeError() {
            throw new RuntimeException("Sensitive database password in secret connection string");
        }
    }

    record SampleRequest(
            @NotBlank(message = "must not be blank")
            String name
    ) {
    }
}
