package repository;

import entity.Student;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;

public class StudentDao {

    Logger logger = Logger.getLogger(StudentDao.class);

    public List<Student> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Student").list();
        } catch (HibernateException e) {
            e.printStackTrace();
            logger.error(e);
        }
        return null;
    }

    public List<Student> getAllSvBirthday() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("select s from Student s where TO_CHAR(current_date,'DD')=to_char(birthday,'DD')").list();
        } catch (HibernateException e) {
            e.printStackTrace();
            logger.error(e);
        }
        return null;
    }

    public Student findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Student> query = session.createQuery("select s from Student s where s.id = :p_student_id");
            query.setParameter("p_student_id", id);
            return query.getSingleResult();
        } catch (HibernateException e) {
            e.printStackTrace();
            logger.error(e);
        }
        return null;
    }

    public boolean insert(Student student) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(student);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            logger.error(e);
        }
        return false;
    }

    public boolean update(Student student,int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Student c=session.get(Student.class,id);
            c.setAverageMark(student.getAverageMark());
            c.setBirthday(student.getBirthday());
            c.setClassName(student.getClassName());
            c.setFullName(student.getFullName());
            c.setGender(student.getGender());
            c.setHometown(student.getHometown());
            c.setMajor(student.getMajor());
            session.save(c);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            logger.error(e);
        }
        return false;
    }

    public boolean removeStudent(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Student student = session.load(Student.class, id);
            session.delete(student);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            logger.error(e);
        } finally {
            session.close();
        }
        return false;
    }

    public List<Student> findByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Student> query= session.createQuery("select s from Student s where s.fullName like: name");
            query.setParameter("name","%"+name+"%");
            return query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
            logger.error(e);
        }
        return null;
    }

}
