package com.green.universityGroup.security;

import java.util.stream.Collectors;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.green.universityGroup.domain.dto.ProfessorListDTO;
import com.green.universityGroup.domain.dto.StudentlistDTO;
import com.green.universityGroup.domain.entity.DepartmentEntity;
import com.green.universityGroup.domain.entity.ProfessorEntity;
import com.green.universityGroup.domain.entity.StudentEntity;
import com.green.universityGroup.domain.entity.UserEntity;

import jakarta.transaction.Transactional;
import lombok.Getter;


//Controller의 메서드에서 @AuthenticationPrincipal CustomUserDetails user
@Getter // principal 에서 확인가능
public class RaraUniversityUserDetails extends User {
	private static final long serialVersionUID = 1L;

	// principal에서 확인하기 위해 추가로 등록할 수 있다
	// password 는 등록x
	private String email; // =username
	private String name; // 한글이름
	private String profileImage;
	private DepartmentEntity department;
	private ProfessorListDTO professorDTO; // 교수 정보
	private StudentlistDTO studentDTO;
	private long user_no;

	// 첫번째 인증객체는

	public RaraUniversityUserDetails(UserEntity entity) {
		super(entity.getEmail(), entity.getPassword(), entity.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toSet()));
		// 추가 등록하는건 아래서 초기화 해주면됨
		email = entity.getEmail();
		name = entity.getUsername();
		profileImage = entity.getProfileImage();
		user_no = entity.getUserNo();
		// Professor DTO 설정
		if (entity.getProfessor() != null) {
			ProfessorEntity professorEntity = entity.getProfessor();
			this.professorDTO = ProfessorListDTO.builder()
					.professorNo(professorEntity.getProfessorNo())
					.professor_number(professorEntity.getProfessorNumber())
					.username(professorEntity.getUser().getUsername())
					.department_name(professorEntity.getDepartment().getDepartmentName()).build();
		}

		if (entity.getStudent() != null) {
			StudentEntity studentEntity = entity.getStudent();
			this.studentDTO = StudentlistDTO.builder()
					.student_no(studentEntity.getStudentNo())
					.student_number(studentEntity.getStudentNumber())
					.username(studentEntity.getUser().getUsername())
					.department_name(studentEntity.getDepartment().getDepartmentName()).build();
		}
	}
}
