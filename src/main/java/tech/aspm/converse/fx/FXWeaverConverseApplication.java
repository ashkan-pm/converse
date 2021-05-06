package tech.aspm.converse.fx;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import tech.aspm.converse.ConverseApplication;

public class FXWeaverConverseApplication extends Application {
	private ConfigurableApplicationContext context;

	@Override
	public void init() throws Exception {
		this.context = new SpringApplicationBuilder().sources(ConverseApplication.class)
				.run(getParameters().getRaw().toArray(new String[0]));
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		context.publishEvent(new StageReadyEvent(primaryStage));
	}

	@Override
	public void stop() throws Exception {
		context.close();
		Platform.exit();
	}
}
