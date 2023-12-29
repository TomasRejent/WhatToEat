package cz.afrosoft.whattoeat.core.gui.component.support;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Marks FXML Custom component. Marked class is set as both root and controller in {@link javafx.fxml.FXMLLoader} used to
 * load components fxml specified by {@link #fxmlPath()}.</p>
 *
 * <p>Both Spring {@link org.springframework.beans.factory.annotation.Autowired} dependencies and fields marked with {@link javafx.fxml.FXML}
 * are injected to marked class.</p>
 *
 * <p>Component class can use injected fields to do its initialization in method marked with {@link jakarta.annotation.PostConstruct}.</p>
 *
 * <p>If component uses custom properties it is recommended that it also implements {@link javafx.util.Builder} and that build method returns component itself.
 * Reason for this is that FXML Loader sets properties to builder, not constructed component. While this approach is valid it would require definition of specific
 * builder for every component and duplicating properties from component inside builder. When component implements builder then properties are set directly to component without
 * need to use a lot of boilerplate. If component does not implement builder, then fallback {@link FXMLComponentBuilder} is used. This builder tries to set properties to component by
 * reflection.</p>
 *
 * @author Tomas Rejent
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public @interface FXMLComponent {

    /**
     * @return (NotBlank) Path to fxml file containing definition of custom component structure.
     */
    String fxmlPath();

}
