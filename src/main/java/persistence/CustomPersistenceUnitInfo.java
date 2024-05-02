package persistence;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.SharedCacheMode;
import jakarta.persistence.ValidationMode;
import jakarta.persistence.spi.ClassTransformer;
import jakarta.persistence.spi.PersistenceUnitInfo;
import jakarta.persistence.spi.PersistenceUnitTransactionType;

import javax.sql.DataSource;
import java.net.URL;
import java.util.List;
import java.util.Properties;

public class CustomPersistenceUnitInfo implements PersistenceUnitInfo {
    private final Properties properties = new Properties();

    public CustomPersistenceUnitInfo() {
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("jakarta.persistence.jdbc.url", "jdbc:postgresql://aws-0-ap-southeast-1.pooler.supabase.com:5432/postgres");
        properties.put("jakarta.persistence.jdbc.user", "postgres.pswhvvykrqmtbcudgjmt");
        properties.put("jakarta.persistence.jdbc.password", "Dungdechepbai135");
        properties.put("hibernate.hikari.maximumPoolSize", "10");
        properties.put("hibernate.hikari.minimumIdle", "5");
        properties.put("hibernate.id.new_generator_mappings", "true");
        properties.put("hibernate.id.optimizer.pooled.table", "sequence_generator_table");

        // Define the generator name and the class to use
        properties.put("hibernate.id.optimizer.pooled.policy_holder_id_generator", "org.nikisurance.util.CustomerIdGenerator");
        properties.put("hibernate.id.optimizer.pooled.dependent_id_generator", "org.nikisurance.util.CustomerIdGenerator");
    }

    @Override
    public String getPersistenceUnitName() {
        return "my-persistence-unit";
    }

    @Override
    public String getPersistenceProviderClassName() {
        return "org.hibernate.jpa.HibernatePersistenceProvider";
    }

    @Override
    public PersistenceUnitTransactionType getTransactionType() {
        return PersistenceUnitTransactionType.RESOURCE_LOCAL;
    }

    @Override
    public DataSource getJtaDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:postgresql://aws-0-ap-southeast-1.pooler.supabase.com:5432/postgres");
        dataSource.setUsername("postgres.pswhvvykrqmtbcudgjmt");
        dataSource.setPassword("Dungdechepbai135");
        return dataSource;
    }

    @Override
    public DataSource getNonJtaDataSource() {
        return null;
    }

    @Override
    public List<String> getMappingFileNames() {
        return List.of();
    }

    @Override
    public List<URL> getJarFileUrls() {
        return List.of();
    }

    @Override
    public URL getPersistenceUnitRootUrl() {
        return null;
    }

    @Override
    public List<String> getManagedClassNames() {
        return List.of("org.nikisurance.entity.Admin",
                "org.nikisurance.entity.PolicyHolder",
                "org.nikisurance.entity.Dependent",
                "org.nikisurance.entity.Claim",
                "org.nikisurance.entity.InsuranceCard",
                "org.nikisurance.entity.PolicyOwner",
                "org.nikisurance.entity.InsuranceSurveyor",
                "org.nikisurance.entity.InsuranceManager");
    }

    @Override
    public boolean excludeUnlistedClasses() {
        return false;
    }

    @Override
    public SharedCacheMode getSharedCacheMode() {
        return null;
    }

    @Override
    public ValidationMode getValidationMode() {
        return null;
    }

    @Override
    public Properties getProperties() {
        return properties;
    }

    @Override
    public String getPersistenceXMLSchemaVersion() {
        return "";
    }

    @Override
    public ClassLoader getClassLoader() {
        return null;
    }

    @Override
    public void addTransformer(ClassTransformer classTransformer) {

    }

    @Override
    public ClassLoader getNewTempClassLoader() {
        return null;
    }
}
