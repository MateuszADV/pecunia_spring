package PecuniaSpring.services.chartServices;

import PecuniaSpring.chartClass.DeclaredMethod;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ChartServiceImpl implements ChartService {

    @Override
    public Map<String, Object> getYamlToObj(String reportName) {
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("static/reportsChart/reports/" + reportName + ".yml");
        Map<String, Object> mapObject = yaml.load(inputStream);

        return mapObject;
    }

    @Override
    public List<DeclaredMethod> declaredMethodList(Class c) {
        List<DeclaredMethod> declaredMethodList = new ArrayList<>();
        Method[] methods = c.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.getName() + " == " + method.getParameters().length);
            declaredMethodList.add(new DeclaredMethod(method.getName(), method.getParameters().length));
        }
        return declaredMethodList;
    }
}
