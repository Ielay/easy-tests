package easytests.core.mappers;

import easytests.config.DatabaseConfig;
import easytests.core.entities.*;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.*;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author SingularityA
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = {"classpath:database.test.properties"})
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {DatabaseConfig.class})
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/mappersTestData.sql")
public class IssueStandardTopicPrioritiesMapperTest {

    @Autowired
    private IssueStandardTopicPrioritiesMapper topicPriorityMapper;

    @Test
    public void testFindAll() throws Exception {
        List<IssueStandardTopicPriorityEntity> topicPriorityEntities = this.topicPriorityMapper.findAll();

        Assert.assertNotNull(topicPriorityEntities);
        Assert.assertEquals(3, topicPriorityEntities.size());
    }

    @Test
    public void testFind() throws Exception {
        final IssueStandardTopicPriorityEntity topicPriorityEntity = this.topicPriorityMapper.find(1);

        Assert.assertEquals((Integer) 1, topicPriorityEntity.getId());
        Assert.assertEquals((Integer) 1, topicPriorityEntity.getTopicId());
        Assert.assertEquals(true, topicPriorityEntity.getIsPreferable());
        Assert.assertEquals((Integer) 1, topicPriorityEntity.getIssueStandardId());
    }

    @Test
    public void testFindByIssueStandardId() throws Exception {
        final Integer issueStandardId = 1;
        final List<IssueStandardTopicPriorityEntity> topicPriorityEntities
                = this.topicPriorityMapper.findByIssueStandardId(issueStandardId);

        Assert.assertNotNull(topicPriorityEntities);
        Assert.assertEquals(2, topicPriorityEntities.size());

        IssueStandardTopicPriorityEntity topicPriorityEntity = topicPriorityEntities.get(1);

        Assert.assertEquals((Integer) 2, topicPriorityEntity.getTopicId());
        Assert.assertEquals(false, topicPriorityEntity.getIsPreferable());
        Assert.assertEquals((Integer) 1, topicPriorityEntity.getIssueStandardId());
    }

    @Test
    public void testInsert() throws Exception {
        final Integer id = this.topicPriorityMapper.findAll().size() + 1;
        final Integer topicId = 4;
        final Boolean isPreferable = true;
        final Integer issueStandardId = 2;

        IssueStandardTopicPriorityEntity topicPriorityEntity = Mockito.mock(IssueStandardTopicPriorityEntity.class);
        Mockito.when(topicPriorityEntity.getTopicId()).thenReturn(topicId);
        Mockito.when(topicPriorityEntity.getIsPreferable()).thenReturn(isPreferable);
        Mockito.when(topicPriorityEntity.getIssueStandardId()).thenReturn(issueStandardId);

        this.topicPriorityMapper.insert(topicPriorityEntity);

        verify(topicPriorityEntity, times(1)).setId(id);

        topicPriorityEntity = this.topicPriorityMapper.find(id);
        Assert.assertEquals(id, topicPriorityEntity.getId());
        Assert.assertEquals(topicId, topicPriorityEntity.getTopicId());
        Assert.assertEquals(isPreferable, topicPriorityEntity.getIsPreferable());
        Assert.assertEquals(issueStandardId, topicPriorityEntity.getIssueStandardId());
    }

    @Test
    public void testUpdate() throws Exception {
        final Integer id = 1;
        final Integer topicId = 2;
        final Boolean isPreferable = false;
        final Integer issueStandardId = 2;

        IssueStandardTopicPriorityEntity topicPriorityEntity = this.topicPriorityMapper.find(id);
        Assert.assertEquals(id, topicPriorityEntity.getId());
        Assert.assertNotEquals(topicId, topicPriorityEntity.getTopicId());
        Assert.assertNotEquals(isPreferable, topicPriorityEntity.getIsPreferable());
        Assert.assertNotEquals(issueStandardId, topicPriorityEntity.getIssueStandardId());

        topicPriorityEntity = Mockito.mock(IssueStandardTopicPriorityEntity.class);
        Mockito.when(topicPriorityEntity.getId()).thenReturn(id);
        Mockito.when(topicPriorityEntity.getTopicId()).thenReturn(topicId);
        Mockito.when(topicPriorityEntity.getIsPreferable()).thenReturn(isPreferable);
        Mockito.when(topicPriorityEntity.getIssueStandardId()).thenReturn(issueStandardId);

        this.topicPriorityMapper.update(topicPriorityEntity);

        topicPriorityEntity = this.topicPriorityMapper.find(id);
        Assert.assertEquals(id, topicPriorityEntity.getId());
        Assert.assertEquals(topicId, topicPriorityEntity.getTopicId());
        Assert.assertEquals(isPreferable, topicPriorityEntity.getIsPreferable());
        Assert.assertEquals(issueStandardId, topicPriorityEntity.getIssueStandardId());
    }

    @Test
    public void testDelete() throws Exception {
        IssueStandardTopicPriorityEntity topicPriorityEntity = this.topicPriorityMapper.find(3);
        Assert.assertNotNull(topicPriorityEntity);

        this.topicPriorityMapper.delete(topicPriorityEntity);
        topicPriorityEntity = this.topicPriorityMapper.find(3);
        Assert.assertNull(topicPriorityEntity);
    }
}
