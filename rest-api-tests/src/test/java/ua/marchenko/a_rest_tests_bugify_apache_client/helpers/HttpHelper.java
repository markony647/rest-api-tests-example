package ua.marchenko.a_rest_tests_bugify_apache_client.helpers;

import org.apache.http.client.fluent.Executor;
import ua.marchenko.a_rest_tests_bugify_apache_client.TestConfig;

public class HttpHelper {

    public Executor getExecutor() {
        return Executor.newInstance().auth(TestConfig.apiKey, "");
    }
}
