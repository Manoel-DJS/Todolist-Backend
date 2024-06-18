package backend.web.todolist.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long projectid;

    @Column(name = "name" ,length = 50)
    private String name;

    @Column(name = "description" ,length = 100)
    private String descriptionProject;


    // FK user
    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;


    public Project(){

    }

    public Project(long projectid, String name, String descriptionProject, User user, List<Task> tasks) {
        this.projectid = projectid;
        this.name = name;
        this.descriptionProject = descriptionProject;
        this.user = user;
        // this.tasks = tasks;
    }

    public long getProjectid() {
        return projectid;
    }

    public void setProjectid(long projectid) {
        this.projectid = projectid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptionProject() {
        return descriptionProject;
    }

    public void setDescriptionProject(String descriptionProject) {
        this.descriptionProject = descriptionProject;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
