package org.example.tasktrackerapi.service;

import org.example.tasktrackerapi.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class SocketService {

    private static final Logger logger = LoggerFactory.getLogger(SocketService.class);

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public SocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendProjectCreationAlert(Project project) {
        if (project != null) {
            logger.info("Sending project creation alert for project ID: {}, Name: {}", project.getId(), project.getName());
            messagingTemplate.convertAndSend("/topic/projects", project);
        } else {
            logger.warn("Attempted to send project creation alert for null project");
        }
    }
}