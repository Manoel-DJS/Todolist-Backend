package backend.web.todolist.controller.dto;

public record CreateTaskDto(Long id,String titleTask, String description, boolean completed) {
}
