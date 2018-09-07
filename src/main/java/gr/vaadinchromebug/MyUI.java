package gr.vaadinchromebug;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;

import java.io.*;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@SpringUI(path = "/bug/")

public class MyUI extends UI {

   private com.vaadin.ui.Upload upload1 = new Upload();
    private com.vaadin.ui.Upload upload2 = new Upload();
    private com.vaadin.ui.Upload upload3 = new Upload();
    private com.vaadin.ui.Upload upload4 = new Upload();
    private com.vaadin.ui.Upload upload5 = new Upload();
    private com.vaadin.ui.Upload upload6 = new Upload();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();

        Upload.Receiver receiver = (filename, mime) -> {

            try {
                File file = File.createTempFile("PREFIX_", "SUFFIX_");

                return new FileOutputStream(file);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        };

        Upload.SucceededListener succeededListener =  (event) -> {
            layout.addComponent(new Label("Loaded " + event.getComponent().getId()));
        };

        upload1.setId("upload1");
        upload1.setReceiver(receiver);
        upload1.addSucceededListener(succeededListener);

        upload2.setId("upload2");
        upload2.setReceiver(receiver);
        upload2.addSucceededListener(succeededListener);

        upload3.setId("upload3");
        upload3.setReceiver(receiver);
        upload3.addSucceededListener(succeededListener);

        upload4.setId("upload4");
        upload4.setReceiver(receiver);
        upload4.addSucceededListener(succeededListener);

        upload5.setId("upload5");
        upload5.setReceiver(receiver);
        upload5.addSucceededListener(succeededListener);

        upload6.setId("upload6");
        upload6.setReceiver(receiver);
        upload6.addSucceededListener(succeededListener);

        layout.addComponents(upload1,upload2,upload3,upload4,upload5,upload6);
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
