package jdev.novid.foundation.config.datasource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Host;
import com.aerospike.client.policy.ClientPolicy;

import jdev.novid.component.asmapper.AerospikeTemplate;

@PropertySource(value = { "classpath:/aerospike.properties" })
public class AerospikeConfiguration {

    @Autowired
    private Environment env;

    @Bean(destroyMethod = "close")
    public AerospikeClient aerospikeClient() {

        // LuaConfig.SourceDirectory =
        // this.env.getProperty("aerospike.luaDirectory");

        // ClassLoader cl = ClassLoader.getSystemClassLoader();
        //
        // String sourceDir = cl.getResource("udf").getPath();
        //
        // LuaConfig.SourceDirectory = sourceDir;

        String hosts = this.env.getProperty("aerospike.hosts");

        int port = Integer.valueOf(this.env.getProperty("aerospike.port"));

        int threadPoolSize = Integer.valueOf(this.env.getProperty("aerospike.threadPoolSize"));

        String[] hostNames = hosts.split("\\|", -1);

        List<Host> hostList = new ArrayList<>();

        for (String hostName : hostNames) {

            Host host = new Host(hostName, port);

            hostList.add(host);

        }

        ClientPolicy policy = new ClientPolicy();

        policy.threadPool = Executors.newFixedThreadPool(threadPoolSize);

        AerospikeClient aerospikeClient = new AerospikeClient(policy, hostList.toArray(new Host[hostList.size()]));

        return aerospikeClient;

    }

    @Bean
    public AerospikeTemplate aerospikeTemplate(AerospikeClient aerospikeClient) {

        return new AerospikeTemplate(aerospikeClient, "novid");

    }

}
