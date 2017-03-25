/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor;

import HPalang.SpaceEx.Core.SpaceExModel;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 *
 * @author Iman Jahandideh
 */
public class SpaceExToXMLConvertor
{

    public String Convert(SpaceExModel model)
    {
        
        String output = new String();
        try {
            SpaceExToXMLConvertorVisitor visitor = new SpaceExToXMLConvertorVisitor();

            model.Accept(visitor);

            DOMSource domSource = new DOMSource(visitor.GetConvertedXML());
            
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            
            output =  writer.toString();

        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(SpaceExToXMLConvertor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(SpaceExToXMLConvertor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return output;
    }
}
