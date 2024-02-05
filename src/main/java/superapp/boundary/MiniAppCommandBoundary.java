package superapp.boundary;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import superapp.entity.command.TargetObject;
import superapp.entity.command.InvokedBy;
import superapp.entity.command.MiniAppCommandEntity;
import superapp.entity.command.CommandId;

public class MiniAppCommandBoundary {
    private String command;
    private TargetObject targetObject;
    private Date invocationTimestamp;
    private InvokedBy invokedBy;
    private Map<String, Object> commandAttributes;

    public MiniAppCommandBoundary() {
        this.commandAttributes = new TreeMap<>();
    }

    public MiniAppCommandBoundary(MiniAppCommandEntity entity) {
        this.command = entity.getCommand();
        this.targetObject = entity.getTargetObject();
        this.invocationTimestamp = entity.getInvocationTimestamp();
        this.invokedBy = entity.getInvokedBy();
        this.commandAttributes = entity.getCommandAttributes();
    }

    public MiniAppCommandBoundary(String command, TargetObject targetObject,
                                  Date invocationTimestamp, InvokedBy invokedBy, Map<String, Object> commandAttributes) {
        super();
        this.command = command;
        this.targetObject = targetObject;
        this.invocationTimestamp = invocationTimestamp;
        this.invokedBy = invokedBy;
        this.commandAttributes = commandAttributes;
    }


    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public TargetObject getTargetObject() {
        return targetObject;
    }

    public void setTargetObject(TargetObject targetObject) {
        this.targetObject = targetObject;
    }

    public Date getInvocationTimestamp() {
        return invocationTimestamp;
    }

    public void setInvocationTimestamp(Date invocationTimestamp) {
        this.invocationTimestamp = invocationTimestamp;
    }

    public InvokedBy getInvokedBy() {
        return invokedBy;
    }

    public void setInvokedBy(InvokedBy invokedBy) {
        this.invokedBy = invokedBy;
    }

    public Map<String, Object> getCommandAttributes() {
        return commandAttributes;
    }

    public void setCommandAttributes(Map<String, Object> commandAttributes) {
        this.commandAttributes = commandAttributes;
    }

    @Override
    public String toString() {
        return "MiniAppCommandBoundary [ command=" + command + ", targetObject="
                + targetObject + ", invocationTimestamp=" + invocationTimestamp + ", invokedBy=" + invokedBy
                + ", commandAttributes=" + commandAttributes + "]";
    }

    public MiniAppCommandEntity toEntity(String superAppName, String miniAppName) {
        MiniAppCommandEntity newEntity = new MiniAppCommandEntity();
        newEntity.setCommandId(new CommandId(superAppName, miniAppName));
        newEntity.setCommandAttributes(commandAttributes);
        newEntity.setCommand(command);
        newEntity.setInvocationTimestamp(invocationTimestamp);
        newEntity.setInvokedBy(invokedBy);
        newEntity.setTargetObject(targetObject);
        return newEntity;
    }

}