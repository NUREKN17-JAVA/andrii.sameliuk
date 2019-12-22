package ua.nure.cs.sameliuk.usermanagment.agent;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import ua.nure.cs.sameliuk.usermanagment.db.DaoFactory;
import ua.nure.cs.sameliuk.usermanagment.db.DataBaseException;
import ua.nure.cs.sameliuk.usermanagment.domain.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class RequestServer extends CyclicBehaviour {
    @Override
    public void action() {
        ACLMessage message = myAgent.receive();
        if (message != null) {
            if (message.getPerformative() == ACLMessage.REQUEST) {
                myAgent.send(createReply(message));
            } else {
                Collection users = parseMessage(message);
                ((SearchAgent) myAgent).showUsers(users);
            }
        } else {
            block();
        }
    }

    private Collection parseMessage(ACLMessage message) {
        Collection<User> users = new LinkedList<>();

        String content = message.getContent();
        if (content != null) {
            StringTokenizer tokenizer = new StringTokenizer(content, ";");
            while (tokenizer.hasMoreTokens()) {
                String userInfo = tokenizer.nextToken();
                StringTokenizer tokenizer1 = new StringTokenizer(userInfo, ",");
                String id  = tokenizer1.nextToken();
                String firstName = tokenizer1.nextToken();
                String lastName = tokenizer1.nextToken();
                users.add(new User(Long.valueOf(id), firstName, lastName, null));
            }
        }

        return users;
    }

    private ACLMessage createReply(ACLMessage message) {
        ACLMessage reply = message.createReply();

        String content = message.getContent();
        StringTokenizer tokenizer = new StringTokenizer(content, ",");

        if (tokenizer.countTokens() == 2) {
            String firstName = tokenizer.nextToken();
            String lastName = tokenizer.nextToken();
            Collection<User> users = null;
            try {
                users = DaoFactory.getInstance().getDao().find(firstName, lastName);
            } catch (ReflectiveOperationException | DataBaseException e) {
                e.printStackTrace();
                users = new ArrayList<>(0);
            }

            StringBuffer buffer = new StringBuffer();
            for (Iterator it = users.iterator(); it.hasNext();) {
                User user = (User) it.next();
                buffer.append(user.getId()).append(",");
                buffer.append(user.getFirstName()).append(",");
                buffer.append(user.getLastName()).append(";");
            }

            reply.setContent(buffer.toString());
        }

        return reply;
    }
}
