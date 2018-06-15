package ua.marchenko.c_github_rest_example;

import com.google.common.collect.ImmutableMap;
import com.jcabi.github.*;

import java.io.IOException;

public class GitHubRestExample {

    public static void main(String[] args) throws IOException {
        Github github = new RtGithub("***************");
        RepoCommits commits = github.repos().
                get(new Coordinates.Simple("markony647", "algorithms-and-data-structures-java"))
                .commits();
        for (RepoCommit commit : commits.iterate(new ImmutableMap.Builder<String, String>().build())) {
            System.out.println(new RepoCommit.Smart(commit).message());
        }
    }
}
