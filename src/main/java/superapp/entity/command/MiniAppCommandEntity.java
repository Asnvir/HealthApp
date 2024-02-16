package superapp.entity.command;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import org.springframework.data.mongodb.core.mapping.Document;
import superapp.boundary.command.MiniAppCommandBoundary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import superapp.boundary.command.MiniAppCommandIdBoundary;

@Document(collection = "MINIAPPSCOMMANDS")
public class MiniAppCommandEntity {

    @Id
    private CommandId commandId;
    private String command;
    private TargetObject targetObject;
    private Date invocationTimestamp;
    private InvokedBy invokedBy;
    private Map<String, Object> commandAttributes;
    private static final Logger logger = LoggerFactory.getLogger(MiniAppCommandEntity.class);

    public MiniAppCommandEntity() {
        this.commandAttributes = new TreeMap<>();
    }

    public MiniAppCommandEntity(CommandId commandId, String command, TargetObject targetObject,
                                Date invocationTimestamp, InvokedBy invokedBy, Map<String, Object> commandAttributes) {
        this.commandId = commandId;
        this.command = command;
        this.targetObject = targetObject;
        this.invocationTimestamp = new Date();
        this.invokedBy = invokedBy;
        this.commandAttributes = commandAttributes;
    }

    private MiniAppCommandEntity(Builder builder) {
        this.commandId = builder.commandId;
        this.command = builder.command;
        this.targetObject = builder.targetObject;
        this.invocationTimestamp = new Date();
        this.invokedBy = builder.invokedBy;
        this.commandAttributes = builder.commandAttributes;
        logger.info("Attributes from new MiniAppCommandEntity:" + this.commandId + "," + this.command + "," + this.targetObject + "," + this.invocationTimestamp + "," + this.invokedBy + "," + this.commandAttributes);
    }

    @Id
    public CommandId getCommandId() {
        return commandId;
    }

    public void setCommandId(CommandId commandId) {
        this.commandId = commandId;
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
    public int hashCode() {
        return Objects.hash(commandId);
    }

    public MiniAppCommandBoundary toBoundary() {
        MiniAppCommandBoundary newBound = new MiniAppCommandBoundary();
        MiniAppCommandIdBoundary commandId = new MiniAppCommandIdBoundary(this.getCommandId().getSuperapp(), this.getCommandId().getMiniApp(), this.getCommandId().getId());
        newBound.setCommandId(commandId);
        newBound.setCommand(command);
        newBound.setTargetObject(targetObject);
        newBound.setInvocationTimestamp(invocationTimestamp);
        newBound.setInvokedBy(invokedBy);
        newBound.setCommandAttributes(commandAttributes);
        return newBound;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MiniAppCommandEntity other = (MiniAppCommandEntity) obj;
        return Objects.equals(commandId, other.commandId);
    }

    @Override
    public String toString() {
        return "MiniAppCommandEntity ["
                + "commandId=" + commandId.toString()
                + ", command=" + command
                + ", targetObject=" + targetObject.toString()
                + ", invocationTimestamp=" + invocationTimestamp
                + ", invokedBy=" + invokedBy.toString()
                + ", commandAttributes=" + commandAttributes.toString()
                + "]";
    }

    // Static inner Builder class
    public static class Builder {
        private CommandId commandId;
        private String command;
        private TargetObject targetObject;
        private Date invocationTimestamp;
        private InvokedBy invokedBy;
        private Map<String, Object> commandAttributes;

        public Builder withCommandId(CommandId commandID) {
            this.commandId = commandID;
            return this;
        }

        public Builder withCommand(String command) {
            this.command = command;
            return this;
        }

        public Builder withTargetObject(TargetObject targetObject) {
            this.targetObject = targetObject;
            return this;
        }

        public Builder withDate() {
            this.invocationTimestamp = new Date();
            return this;
        }

        public Builder withInvokedBy(InvokedBy invokedBy) {
            this.invokedBy = invokedBy;
            return this;
        }

        public Builder withCommandAttributes(Map<String, Object> commandAttributes) {
            this.commandAttributes = commandAttributes;
            return this;
        }

        public MiniAppCommandEntity build() {
            return new MiniAppCommandEntity(this);
        }
    }
}