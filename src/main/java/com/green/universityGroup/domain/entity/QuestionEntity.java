package com.green.universityGroup.domain.entity;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@DynamicUpdate
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Getter
@Table(name = "question")
@Entity
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 카테고리 필드
    @Column(nullable = false)
    private String category;

    // 카테고리에 대한 질문
    @Column(nullable = false)
    private String text;

    // 사전 정의된 답변을 저장하는 필드
    @Column(nullable = false)
    private String answer;

    // 부모 질문과의 셀프 조인을 위한 필드
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId")
    private QuestionEntity parent;

    // 자식 질문들과의 셀프 조인을 위한 필드
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<QuestionEntity> children;
}
