package com.codesquad.issuetracker.api.milestone.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.codesquad.issuetracker.api.milestone.domain.Milestone;
import com.ulisesbocchio.jasyptspringbootstarter.JasyptSpringBootAutoConfiguration;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ComponentScan("com.codesquad.issuetracker.api.milestone.repository")
@SpringJUnitConfig(classes = JasyptSpringBootAutoConfiguration.class)
class MilestoneRepositoryTest {

    public static final long ORGANIZATION_ID = 1L;
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final int EXPECTED_MILESTONE_ID = 1;
    public static final LocalDate DUE_DATE = LocalDate.of(2023, 8, EXPECTED_MILESTONE_ID);

    @Autowired
    MilestoneRepositoryImpl milestoneRepository;

    @Nested
    @DisplayName("마일스톤을 저장한다")
    class SaveMilestoneTest {

        Milestone milestone;
        Long milestoneId;

        @BeforeEach
        void setUp() {
            milestone = Milestone.builder()
                .organizationId(ORGANIZATION_ID)
                .title(TITLE)
                .description(DESCRIPTION)
                .dueDate(DUE_DATE)
                .isClosed(false)
                .build();

            milestoneId = milestoneRepository.save(milestone).orElseThrow();
            assertThat(milestoneId).isNotNull();
        }

        @DisplayName("마일스톤을 삭제 하면")
        @Nested
        class DeleteTest {

            @BeforeEach
            void setUp() {
                milestoneRepository.deleteById(milestoneId);
            }

            @DisplayName("해당 마일스톤을 찾을 수 없다")
            @Test
            void cannotFindMilestone() {
                assertThat(milestoneRepository.findById(milestoneId)).isEmpty();
            }
        }


        @DisplayName("마일스톤을 업데이트 하면")
        @Nested
        class UpdateTest {

            public static final String CHANGED_TITLE = "changedTitle";
            public static final String CHANGED_DESCRIPTION = "changedDescription";
            public final LocalDate CHANGED_DUE_DATE = LocalDate.of(2023, 8, 2);

            @BeforeEach
            void setUp() {
                Milestone changedMilestone = Milestone.builder()
                    .id(milestoneId)
                    .title(CHANGED_TITLE)
                    .description(CHANGED_DESCRIPTION)
                    .dueDate(CHANGED_DUE_DATE)
                    .build();
                milestoneRepository.update(changedMilestone);
            }

        }
    }
}
