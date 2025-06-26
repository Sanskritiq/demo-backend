package com.example.demo.webhook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class JiraWebhookController {
    private static final Logger logger = LoggerFactory.getLogger(JiraWebhookController.class);

    // Endpoint to receive Jira webhooks
    @PostMapping("/jira-webhook")
    public ResponseEntity<String> handleJiraWebhook(
            @RequestHeader Map<String, String> headers,
            @RequestBody Map<String, Object> payload) {
        // Log headers (e.g., Jira event name)
        logger.info("Received Jira webhook headers: {}", headers);

        // Extract issue data
        Map<String, Object> issue = (Map<String, Object>) payload.get("issue");
        if (issue != null) {
            String key = (String) issue.get("key");
            Map<String, Object> fields = (Map<String, Object>) issue.get("fields");
            String summary = (String) fields.get("summary");
            logger.info("Issue created/updated: {} - {}", key, summary);
            // TODO: invoke Ollama generation script here
        }
        return ResponseEntity.ok("Webhook received");
    }
}
