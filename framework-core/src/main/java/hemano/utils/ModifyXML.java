package hemano.utils;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;


/**
 * Created by hemantojha on 10/08/16.
 */
public class ModifyXML {

    Document document;

    public ModifyXML(String filePath) {
        try {
            File inputFile = new File(filePath);
            SAXReader reader = new SAXReader();
            document = reader.read(inputFile);

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public ModifyXML(File inputFile) {
        try {
            SAXReader reader = new SAXReader();
            document = reader.read(inputFile);

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public void updateNodeValue(String xPath, String newValue) throws Exception {
        try {
            Node node = document.selectSingleNode(xPath);
            node.setText(newValue);
        } catch (Exception e) {
            throw new Exception("Unable to set node value. Incorrect xPath value : " + xPath + "\n" +
                    "OR Input parameter \n" + e);
        }

    }

    public void updateAttributeValue(String xPath, String attrName, String newValue) throws Exception {
        try {

            Node node = document.selectSingleNode(xPath);
            Element element = (Element) node;
            if (null != element.attribute(attrName)) {
                element.addAttribute(attrName, newValue);
            } else {
                throw new Exception("Unable to find attribute value for attribute name: " + attrName);
            }

        } catch (Exception e) {
            throw new Exception("Unable to find node or attribute value. xPath  : " + xPath + "\n" + e);
        }
    }

    public String getXML() {
        return document.asXML().toString();
    }


    public static String getPrettyXML(String xml) throws DocumentException, IOException {
        try {
            Document doc = DocumentHelper.parseText(xml);
            StringWriter sw = new StringWriter();
            OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter xw = new XMLWriter(sw, format);
            xw.write(doc);
            return sw.toString();
        } catch (DocumentException | IOException e) {
            throw new DocumentException("XML cannot be converted to pretty. XML Response : \n" + xml, e);
        }
    }

    public void updateAttributes(String xPath, String attrKeyToSelect, String attrValueToSelect, String attrToModify, String attrNewValue) {

//        disableAllStaticRoutes(xPath);

        List<Node> nodes = document.selectNodes(xPath);
        for (Node node : nodes) {
            Element element = (Element) node;
            if (element.attribute(attrKeyToSelect).getValue().equals(attrValueToSelect)) {
                element.addAttribute(attrToModify, attrNewValue);
            }
        }
    }

    public void updateAttributes(String xPath, String attrToModify, String attrNewValue) {

        List<Node> nodes = document.selectNodes(xPath);
        for (Node node : nodes) {
            Element element = (Element) node;
            element.addAttribute(attrToModify, attrNewValue);
        }
    }

//    private void disableAllStaticRoutes(String xPath) {
//
//        List<Node> nodes = document.selectNodes(xPath);
//        for (Node node : nodes) {
//            Element element = (Element) node;
//
//            if (!element.attribute("psp-id").getValue().equals("1")) {
//                element.addAttribute("enabled", "false");
//            }
//        }
//    }
//public short void updateAtrributes(){
//        xPath = "save-client-configuration-request/client-config/payment-methods/payment-method";
////xPath = "save-client-configuration-request/client-config/payment-methods";
////Node node = document.selectSingleNode(xPath);
//        List<Node> nodes = document.selectNodes(xPath);
//        List<String> psps = new ArrayList<>();
//        for(Node node: nodes){
//
//            Element element = (Element) node;
//
//            psps.add(element.attribute("psp-id").getValue());
//
//            if(!element.attribute("psp-id").getValue().equals("1")){
//                element.addAttribute("enabled", "false");
//            }
//        }
//
//        getXML();
//
//        for(Node node: nodes){
//
//            Element element = (Element) node;
//
//            if(element.attribute("psp-id").getValue().equals("18")){
//                element.addAttribute("enabled", "true");
//            }
//        }
//
//        getXML();
//    }


}

//class TestClass{
//
//    public static void main(String[] args) throws Exception{
//        ModifyXML modifyXML = new ModifyXML("mConsole/src/test/resources/data/initialize_payment.xml");
//        modifyXML.updateNodeValue("/root/initialize-payment/transaction/amount", "300");
//        modifyXML.updateAttributeValue("/root/initialize-payment/transaction",
//                "type-id", "newTypeId");
//        System.out.println("modifyXML.getXML() = " + modifyXML.getXML());
//    }
//
//}
