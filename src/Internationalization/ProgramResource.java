package Internationalization;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.ResourceBundle;

public class ProgramResource extends ResourceBundle{
	
	private String[] keys = {"translateButton"};
	
	@Override
	public Enumeration<String> getKeys() {
	
		return Collections.enumeration(Arrays.asList(keys));
	}
	
	@Override
	protected Object handleGetObject(String key) {
	
		return keys;
	}

	

}
