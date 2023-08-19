package testUtil;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

@Sources({
	"file:src/test/resources/properties/config.properties"
})
public interface ConfigProperty extends Config{
	
	@Key("baseURI")
	String getBaseURI();
	
	@Key("basePath")
	String getBasePath();
	
	@Key("secretKey")
	String getSecretKey();
	
}
