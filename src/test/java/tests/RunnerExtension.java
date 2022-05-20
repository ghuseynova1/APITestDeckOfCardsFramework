package tests;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class RunnerExtension implements AfterTestExecutionCallback {

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        Boolean testResult = context.getExecutionException().isPresent();
        System.out.println(testResult); //false - SUCCESS, true - FAILED
    }
}
