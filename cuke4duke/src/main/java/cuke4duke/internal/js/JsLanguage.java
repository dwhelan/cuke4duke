package cuke4duke.internal.js;

import cuke4duke.internal.language.LanguageMixin;
import cuke4duke.internal.language.ProgrammingLanguage;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeFunction;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.regexp.NativeRegExp;
import org.mozilla.javascript.tools.shell.Global;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class JsLanguage extends ProgrammingLanguage {
    private static final String JS_DSL = "/cuke4duke/internal/js/js_dsl.js";
    private final List<String> jsFiles = new ArrayList<String>();
    private Context cx;
    private Scriptable scope;

    public JsLanguage(LanguageMixin languageMixin) throws Exception {
        super(languageMixin);
    }

    public void load_code_file(String jsFile) throws Throwable {
        jsFiles.add(jsFile);
    }

    protected void prepareScenario() throws Throwable {
        clearHooksAndStepDefinitions();
        cx = Context.enter();
        scope = new Global(cx); // This gives us access to global functions like load()
        scope.put("jsLanguage", scope, this);
        cx.evaluateReader(scope, new InputStreamReader(getClass().getResourceAsStream(JS_DSL)), JS_DSL, 1, null);
        for (String jsFile : jsFiles) {
            cx.evaluateReader(scope, new FileReader(jsFile), jsFile, 1, null);
        }
    }

    public void addStepDefinition(Global jsStepDefinition, NativeFunction argumentsFrom, NativeRegExp regexp, NativeFunction closure) throws Exception {
        addStepDefinition(new JsStepDefinition(this, cx, scope, jsStepDefinition, argumentsFrom, regexp, closure));
    }

    public void cleanupScenario() throws Throwable {
    }
}
