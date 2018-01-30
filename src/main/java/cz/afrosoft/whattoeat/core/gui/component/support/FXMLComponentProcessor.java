package cz.afrosoft.whattoeat.core.gui.component.support;

import cz.afrosoft.whattoeat.core.gui.FXMLLoaderFactory;
import javafx.fxml.FXMLLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Processor for annotation {@link FXMLComponent}.
 * Loads fxml for annotated custom component.
 *
 * @author Tomas Rejent
 */
@Component
public class FXMLComponentProcessor implements BeanPostProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(FXMLComponentProcessor.class);

    @Autowired
    private FXMLLoaderFactory fxmlLoaderFactory;

    @Override
    public Object postProcessBeforeInitialization(final Object bean, final String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(FXMLComponent.class)) {
            LOGGER.trace("Processing bean with FXMLComponent annotation: {}[{}]", beanName, bean);
            FXMLComponent annotation = bean.getClass().getAnnotation(FXMLComponent.class);
            FXMLLoader fxmlLoader = fxmlLoaderFactory.createFXMLLoader(annotation.fxmlPath());
            fxmlLoader.setRoot(bean);
            fxmlLoader.setController(bean);
            try {
                fxmlLoader.load();
            } catch (IOException e) {
                LOGGER.error("Processing of FXMLComponent bean failed. Bean: {}[{}]", beanName, bean, e);
                throw new FatalBeanException("Cannot process FXMLComponent.", e);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
        return bean;
    }
}
