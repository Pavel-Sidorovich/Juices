package bsu.fpmi.chat.controller;

import static bsu.fpmi.chat.util.TaskUtil.MESSAGES;
import static bsu.fpmi.chat.util.TaskUtil.TOKEN;
import static bsu.fpmi.chat.util.TaskUtil.getIndex;
import static bsu.fpmi.chat.util.TaskUtil.getToken;
import static bsu.fpmi.chat.util.TaskUtil.jsonToTask;
import static bsu.fpmi.chat.util.TaskUtil.stringToJson;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.log4j.Logger;
import bsu.fpmi.chat.model.Mess;
import bsu.fpmi.chat.model.MessStorage;
import bsu.fpmi.chat.storage.xml.XMLHistoryUtil;
import bsu.fpmi.chat.util.ServletUtil;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

@WebServlet("/chat")
public class TaskServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(TaskServlet.class.getName());

    @Override
    public void init() throws ServletException {
        try {
            loadHistory();
        } catch (SAXException e) {
            logger.error(e);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("doGet");
        String token = request.getParameter(TOKEN);
        logger.info("Token " + token);

        if (token != null && !"".equals(token)) {
            int index = getIndex(token);
            logger.info("Index " + index);
            String tasks = formResponse(index);
            logger.info(tasks);
            response.setCharacterEncoding(ServletUtil.UTF_8);
            response.setContentType(ServletUtil.APPLICATION_JSON);
            PrintWriter out = response.getWriter();
            out.print(tasks);
            out.flush();
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "'token' parameter needed");
        }
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("doPost");
        String data = ServletUtil.getMessageBody(request);
        try {
            JSONObject json = stringToJson(data);
            Mess mess = jsonToTask(json);
            logger.info(mess.getUser() + " : " + mess.getMessage());
            MessStorage.addTask(mess);
            XMLHistoryUtil.addData(mess);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (ParseException e) {
            logger.error(e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("doPut");
        String data = ServletUtil.getMessageBody(request);
        logger.info(data);
        try {
            JSONObject json = stringToJson(data);
            Mess mess = jsonToTask(json);
            String id = mess.getId();
            Mess taskToUpdate = MessStorage.getTaskById(id);
            if (taskToUpdate != null) {
                taskToUpdate.setMessage(mess.getMessage());
                taskToUpdate.setUser(mess.getUser());
                XMLHistoryUtil.updateData(taskToUpdate);
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Task does not exist");
            }
        } catch (ParseException e) {
            logger.error(e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("doDelete");
        String data = ServletUtil.getMessageBody(request);
        try {
            JSONObject json = stringToJson(data);
            Mess mess = jsonToTask(json);
            String id = mess.getId();
            Mess taskToUpdate = MessStorage.getTaskById(id);
            if (taskToUpdate != null) {
                XMLHistoryUtil.deleteData(taskToUpdate);
                taskToUpdate.setMessage("");
                taskToUpdate.setUser("");
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Task does not exist");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private String formResponse(int index) {
        JSONObject jsonObject = new JSONObject();
        logger.info(MessStorage.getSize());
        jsonObject.put(MESSAGES, MessStorage.getSubTasksByIndex(index));
        jsonObject.put(TOKEN, getToken(MessStorage.getSize()));
        return jsonObject.toJSONString();
    }

    private void loadHistory() throws SAXException, IOException, ParserConfigurationException, TransformerException  {
        if (XMLHistoryUtil.doesStorageExist()) {
            MessStorage.addAll(XMLHistoryUtil.getTasks());
        } else {
            XMLHistoryUtil.createStorage();
            addStubData();
        }
    }

    private void addStubData() throws ParserConfigurationException, TransformerException {
        Mess[] stubMesses = {};
        MessStorage.addAll(stubMesses);
        for (Mess mess : stubMesses) {
            try {
                XMLHistoryUtil.addData(mess);
            } catch (ParserConfigurationException e) {
                logger.error(e);
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}