package easytests.core.mappers;

import easytests.config.DatabaseConfig;
import easytests.core.entities.QuizEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author vkpankov
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = {"classpath:database.test.properties"})
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {DatabaseConfig.class})
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/mappersTestData.sql")
public class QuizzesMapperTest {

    @Autowired
    private QuizzesMapper quizzesMapper;

    @Test
    public void testFind() throws Exception {

        final QuizEntity quiz = this.quizzesMapper.find(1);

        Assert.assertEquals((long) 1, (long) quiz.getId());
        Assert.assertEquals("test_invite_code1", quiz.getInviteCode());
        Assert.assertEquals(LocalDateTime.of(2003,2,1,0,0,0), quiz.getStartedAt());
        Assert.assertEquals(LocalDateTime.of(2003,3,1,0,0,0), quiz.getFinishedAt());
        Assert.assertEquals(false, quiz.getCodeExpired());
    }

    @Test
    public void testFindAll() throws Exception {

        final List<QuizEntity> quizEntities = this.quizzesMapper.findAll();

        Assert.assertNotNull(quizEntities);
        Assert.assertEquals((long) 3, (long) quizEntities.size());

    }

    @Test
    public void testIssueNotNull() throws Exception {

        final List<QuizEntity> quizEntities = this.quizzesMapper.findByIssueId(4);

        Assert.assertNotNull(quizEntities);
        Assert.assertEquals(0, quizEntities.size());

    }

    @Test
    public void testFindByIssueId() throws Exception {

        final List<QuizEntity> quizEntities = this.quizzesMapper.findByIssueId(3);

        Assert.assertEquals(1, quizEntities.size());
        Assert.assertEquals("test_invite_code3", quizEntities.get(0).getInviteCode());
        Assert.assertEquals(LocalDateTime.of(2003,2,1,0,0,0), quizEntities.get(0).getStartedAt());
        Assert.assertEquals(LocalDateTime.of(2003,3,1,0,0,0), quizEntities.get(0).getFinishedAt());
        Assert.assertEquals(true, quizEntities.get(0).getCodeExpired());
    }

    @Test
    public void testInsert() throws Exception {
        final Integer id = this.quizzesMapper.findAll().size() + 1;

        final Integer testIssueId = 1;

        final String testInviteCode = "test";

        final LocalDateTime testStartedAt = LocalDateTime.of(2017,5,18,12,1,0);

        final LocalDateTime testFinishedAt = LocalDateTime.of(2017,5,18,13,0,0);

        final boolean testCodeExpired = false;


        final QuizEntity testQuiz = Mockito.mock(QuizEntity.class);

        Mockito.when(testQuiz.getId()).thenReturn(id);
        Mockito.when(testQuiz.getInviteCode()).thenReturn(testInviteCode);
        Mockito.when(testQuiz.getIssueId()).thenReturn(testIssueId);
        Mockito.when(testQuiz.getStartedAt()).thenReturn(testStartedAt);
        Mockito.when(testQuiz.getFinishedAt()).thenReturn(testFinishedAt);
        Mockito.when(testQuiz.getCodeExpired()).thenReturn(testCodeExpired);

        quizzesMapper.insert(testQuiz);

        verify(testQuiz, times(1)).setId(id);

        final QuizEntity readQuiz = quizzesMapper.find(testQuiz.getId());

        Assert.assertNotNull(readQuiz);
        Assert.assertEquals(testIssueId, readQuiz.getIssueId());
        Assert.assertEquals(testInviteCode, readQuiz.getInviteCode());
        Assert.assertEquals(testStartedAt, readQuiz.getStartedAt());
        Assert.assertEquals(testFinishedAt, readQuiz.getFinishedAt());
        Assert.assertEquals(testCodeExpired, readQuiz.getCodeExpired());
    }

    @Test
    public void testUpdate() throws Exception {

        final Integer id = 2;

        final String inviteCode = "updated";

        final LocalDateTime testStartedAt = LocalDateTime.of(2017,5,18,12,1,0);

        final LocalDateTime testFinishedAt = LocalDateTime.of(2017,5,18,13,0,0);

        final boolean testCodeExpired = false;

        QuizEntity quiz = this.quizzesMapper.find(id);

        Assert.assertNotEquals(inviteCode, quiz.getInviteCode());

        quiz = Mockito.mock(QuizEntity.class);

        Mockito.when(quiz.getId()).thenReturn(id);
        Mockito.when(quiz.getInviteCode()).thenReturn(inviteCode);
        Mockito.when(quiz.getStartedAt()).thenReturn(testStartedAt);
        Mockito.when(quiz.getFinishedAt()).thenReturn(testFinishedAt);
        Mockito.when(quiz.getCodeExpired()).thenReturn(testCodeExpired);

        this.quizzesMapper.update(quiz);

        final QuizEntity readQuiz = quizzesMapper.find(id);
        Assert.assertEquals(inviteCode, readQuiz.getInviteCode());
        Assert.assertEquals(testStartedAt, readQuiz.getStartedAt());
        Assert.assertEquals(testFinishedAt, readQuiz.getFinishedAt());
        Assert.assertEquals(testCodeExpired, readQuiz.getCodeExpired());

    }

    @Test
    public void testDelete() throws Exception {
        QuizEntity quiz = this.quizzesMapper.find(1);
        Assert.assertNotNull(quiz);
        this.quizzesMapper.delete(quiz);
        quiz = this.quizzesMapper.find(1);
        Assert.assertNull(quiz);
    }}
