package controller;

import entity.Student;
import service.StudentService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/students")
public class StudentController {

    StudentService studentService = new StudentService();

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Student> getListStudent() {
        return studentService.getListStudent();
    }

    @GET
    @Path("/birthday")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Student> getListStudentBirthday() {
        return studentService.getListStudentBirthday();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Student getStudent(@PathParam("id") int id) {
        return studentService.findID(id);
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Student> findByName(Student student) {
        return studentService.findByName(student.getFullName());
    }

    @POST
    @Path("/post")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String addNewStudent(Student student) {
        if(student.getFullName().equals("")) return "Chưa nhập tên sinh viên";
        else if(student.getFullName().length()>=50) return "Tên không được quá 50 kí tự";

        return studentService.insert(student) ? "Thêm mới thành công" : "Thêm mới thất bại";
    }

    @PUT
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateNewStudent(Student student) {
        if(student.getFullName().equals("")) return "Tên không được để trống";
        else if(student.getFullName().length()>=50) return "Tên không được quá 50 kí tự";
        return studentService.update(student) ? "Thành công" : "Thất bại";
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String removeStudent(@PathParam("id") int id) {
        return studentService.removeStudent(id) ? "Xóa thành công" : "Xóa thất bại";
    }

}