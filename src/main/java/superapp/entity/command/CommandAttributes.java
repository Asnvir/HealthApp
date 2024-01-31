package superapp.entity.command;

import java.io.Serializable;

public class CommandAttributes implements Serializable
{
	private CommandSubAttribute key1;

	public CommandAttributes() {
	};

	public CommandAttributes(CommandSubAttribute key1) {
		this.key1 = key1;
	}

	public CommandSubAttribute getKey1() {
		return key1;
	}

	public void setKey1(CommandSubAttribute key1) {
		this.key1 = key1;
	}

	@Override
	public String toString() {
		return "commandAttributes ["
				+ "key1=" + key1.toString()
				+ "]";
	}

}