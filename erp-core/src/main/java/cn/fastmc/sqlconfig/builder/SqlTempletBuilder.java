package cn.fastmc.sqlconfig.builder;

import java.io.InputStreamReader;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import cn.fastmc.core.SqlConfigBuilderException;
import cn.fastmc.sqlconfig.base.SQLConfiguration;
import cn.fastmc.sqlconfig.base.SqlStatement;

public class SqlTempletBuilder implements InitializingBean,DisposableBean{
	
	private final SQLConfiguration configuration;
	  
	private Resource[] sqlMappsLocations;
	  
	public SqlTempletBuilder(){
		configuration = new SQLConfiguration();
	}
	
	public void init(){
		try{
			if (this.sqlMappsLocations != null) {
			    for(int i = 0; i < this.sqlMappsLocations.length; i++){
					 XmlSqlCofingbuilder xml = new XmlSqlCofingbuilder(new InputStreamReader(sqlMappsLocations[i].getInputStream(),"UTF-8"),configuration);
					 xml.parse();
			      }
				}
		}catch(Exception e){
			  throw new SqlConfigBuilderException(e);
		}
		
	}
	
	public  SqlStatement find(String key){
		SqlStatement statement = configuration.getMappedStatement(key);
		if(statement == null){
			 throw new SqlConfigBuilderException("Unknown sql <" + key + "> in SQL XML.");
		}
		return statement;
	}
	
	
    
	public Resource[] getSqlMappsLocations() {
		return sqlMappsLocations;
	}

	public void setSqlMappsLocations(Resource[] sqlMappsLocations) {
		this.sqlMappsLocations = sqlMappsLocations;
	}
	
	public void setSqlMappsFilePath(String[] configLocations) {
		this.sqlMappsLocations = new Resource[configLocations.length];
		for (int i = 0; i < configLocations.length; i++) {
			this.sqlMappsLocations[i] = new ClassPathResource(configLocations[i].trim());
		}
	}
	
	public void afterPropertiesSet() throws Exception {
		init();
	}
	
	public void destroy() throws Exception {
		
	}
	
      
}
