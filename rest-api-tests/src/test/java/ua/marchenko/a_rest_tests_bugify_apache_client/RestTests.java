package ua.marchenko.a_rest_tests_bugify_apache_client;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.testng.annotations.Test;
import ua.marchenko.a_rest_tests_bugify_apache_client.helpers.HttpHelper;
import ua.marchenko.a_rest_tests_bugify_apache_client.helpers.JsonHelper;

import java.io.IOException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class RestTests {


    JsonHelper jsonHelper = new JsonHelper();
    HttpHelper httpHelper = new HttpHelper();

    @Test
    public void testCreateIssue() throws IOException {
        Set<Issue> oldIssues = getIssues();
        Issue newIssue = new Issue().withSubject("Test Subj").withDescription("Test Description");
        int issueId = createIssue(newIssue);
        Set<Issue> newIssues = getIssues();
        oldIssues.add(newIssue.withId(issueId));
        assertEquals(newIssues, oldIssues);
    }

    @Test
    public void testGetCreatedIssueById() throws IOException {
        Issue issue = new Issue();
        issue.withSubject("1111").withDescription("1111 Descr");
        int newIssueId = createIssue(issue);
        String json = httpHelper.getExecutor().execute(Request.Get(TestConfig.baseUrl + "/issues/" + newIssueId + ".json"))
                .returnContent().asString();
        JsonArray issuesArray = new JsonParser().parse(json).getAsJsonObject().getAsJsonArray("issues");
        Issue returned = new Issue();
        for (int i = 0; i < issuesArray.size(); i++) {
            String description = issuesArray.get(0).getAsJsonObject().get("description").getAsString();
            String subject = issuesArray.get(0).getAsJsonObject().get("subject").getAsString();
            returned.withDescription(description);
            returned.withSubject(subject);
        }
        assertEquals(returned, issue);
    }

    private Set<Issue> getIssues() throws IOException {
        String json = httpHelper.getExecutor().execute(Request.Get(TestConfig.baseUrl + "/issues.json" + "?limit=5")).returnContent().asString();
        JsonElement parsed = new JsonParser().parse(json);
        JsonElement issues = parsed.getAsJsonObject().get("issues");
        return new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType());

    }


    private int createIssue(Issue newIssue) throws IOException {
        String json = httpHelper.getExecutor().execute(Request.Post(TestConfig.baseUrl + "/issues.json").
                bodyForm(new BasicNameValuePair("subject", newIssue.getSubject()),
                        new BasicNameValuePair("description", newIssue.getDescription())))
                .returnContent()
                .asString();
        return jsonHelper.stringJsonToJsonObject(json).get("issue_id").getAsInt();
    }
}
