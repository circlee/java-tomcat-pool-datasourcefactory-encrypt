package com.dklee.tomcat.jdbc.pool;


import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import javax.crypto.NoSuchPaddingException;
import javax.naming.Context;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.DataSourceFactory;
import org.apache.tomcat.jdbc.pool.PoolConfiguration;
import org.apache.tomcat.jdbc.pool.XADataSource;

import com.dklee.util.Encryptor;

/**
 *  참조 : http://www.jdev.it/encrypting-passwords-in-tomcat/
 * <pre>
 * Package Name : com.dklee.tomcat.jdbc.pool
 * Class Name : EncryptDataSource.java
 * 
 * Explanation :
 * </pre>
 *
 * @Author : mac
 * @Since : 2017. 11. 15.
 * @Version :
 */
public class EncryptDataSourceFactory extends DataSourceFactory{


	
	private Encryptor encryptor = null;
	 
    public EncryptDataSourceFactory() {
        try {
            encryptor = new Encryptor();
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }


	@Override
	public DataSource createDataSource(Properties properties,Context context, boolean XA) throws Exception {
		PoolConfiguration poolProperties = DataSourceFactory.parsePoolProperties(properties);

		
		// decrypt url/username/password
		poolProperties.setUrl(encryptor.decrypt(poolProperties.getUrl()));
		poolProperties.setUsername(encryptor.decrypt(poolProperties.getUsername()));
		poolProperties.setPassword(encryptor.decrypt(poolProperties.getPassword()));

		
		if (poolProperties.getDataSourceJNDI()!=null && poolProperties.getDataSource()==null) {
			performJNDILookup(context, poolProperties);
		}

		DataSource dataSource = XA? new XADataSource(poolProperties) : new DataSource(poolProperties);

		//initialise the pool itself
		dataSource.createPool();
		// Return the configured DataSource instance
		return dataSource;
	}



}
