package backend.web.todolist.controller.dto;

public record CreateTaskDto(String titleTask, String description, boolean completed) {
}
