package toby.springbook;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import toby.springbook.user.dao.DConnectionMaker;
import toby.springbook.user.dao.UserDao;
import toby.springbook.user.domain.User;

import java.sql.SQLException;

@SpringBootApplication
public class SpringbookApplication {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		UserDao dao = new UserDao(new DConnectionMaker());

		User user = new User();
		user.setId("whiteship");
		user.setName("백기선");
		user.setPassword("married");

		dao.add(user);

		System.out.println(user.getId() + " 등록 성공");

		User user2 = dao.get(user.getId());
		System.out.println(user2.getName());
		System.out.println(user2.getPassword());

		System.out.println(user2.getId() + " 조회 성공");
	}

	/*
	public static void main(String[] args) {
		SpringApplication.run(SpringbookApplication.class, args);
	}
	*/
}
