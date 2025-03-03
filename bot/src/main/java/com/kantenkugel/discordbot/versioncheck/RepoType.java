package com.kantenkugel.discordbot.versioncheck;

public enum RepoType
{
    REPO_MATTMALEC("mattmalec-repo", "https://repo.mattmalec.com/repository/releases", null),
    MAVENCENTRAL("central", "https://repo.maven.apache.org/maven2", "mavenCentral");
    private final String name;
    private final String repoBase;
    private final String gradleName;

    RepoType(String name, String repoBase, String gradleName)
    {
        this.name = name;
        this.repoBase = repoBase;
        this.gradleName = gradleName;
    }

    public String getName() {
        return name;
    }

    public String getRepoBase()
    {
        return repoBase;
    }

    public String getGradleName() {
        return gradleName;
    }

    public static RepoType fromString(String value)
    {
        value = value.toLowerCase();
        for (RepoType repoType : RepoType.values())
        {
            if (repoType.toString().equals(value) || repoType.getName().toLowerCase().equals(value))
            {
                return repoType;
            }
        }
        return null;
    }

    @Override
    public String toString()
    {
        return name().toLowerCase();
    }
}
