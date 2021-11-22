package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(StudentRepository repository, MongoTemplate mongoTemplate){

		return args -> {

			Address address = new Address(
					"Italy",
					"Rome",
					"00036"
			);
			String email = "leo@gmail.com";
			Student student = new Student(
					"leo",
					"petr",
					email,
					Gender.MALE,
					address,
					List.of("Computer Science", "Maths"),
					BigDecimal.TEN,
					LocalDateTime.now()
			);

			//Custom query
//			usingMongoTemplateAndQuery(repository, mongoTemplate, email, student);

			repository.findStudentByEmail(email).ifPresentOrElse(s->{

				System.out.println(s + "already exists");

			}, ()->{

				System.out.println("Inserting student " + student);
				repository.insert(student);

			});

		};
	}

	private void usingMongoTemplateAndQuery(StudentRepository repository, MongoTemplate mongoTemplate, String email, Student student) {
		Query query = new Query();
		query.addCriteria(Criteria.where("email").is(email));

		List<Student> students = mongoTemplate.find(query, Student.class);

		if(students.size() > 1){

			throw new IllegalStateException("Found many students with same email " + email);
		}
		if(students.isEmpty()){
			System.out.println("Inserting student " + student);
			repository.insert(student);
		}else{
			System.out.println(student + "already exists");
		}
	}
}
