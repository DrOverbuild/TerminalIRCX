/*
 $ Copyright (c) 2014 Jasper Reddin
 $ All rights reserved.
 */
package terminalirc.command;

/**
 * Created by jasper on 1/27/14.
 */
public interface Command {
	
	boolean execute(String[] args);

	String getName();
	String getShortcut();
	String getUsage();
	String getDesc();

}