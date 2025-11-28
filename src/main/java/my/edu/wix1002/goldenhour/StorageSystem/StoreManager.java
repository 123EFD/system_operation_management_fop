package my.edu.wix1002.goldenhour.StorageSystem;

import my.edu.wix1002.goldenhour.model.Employee;
import my.edu.wix1002.goldenhour.model.Model;
import my.edu.wix1002.goldenhour.model.Outlet;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class StoreManager {

    private static final String EMPLOYEE_FILE_PATH = "data/employee.csv";

    //SAVE EMPLOYEES
    public static void saveEmployees(List<Employee> employees) {
        try(CSVWriter writer = new CSVWriter(new FileWriter(EMPLOYEE_FILE_PATH))){

            //write header
            writer.writeNext(new String[]{"EmployeeID", "EmployeeName", "Role", "Password", "OutletCode"});

            //write each employee
            for (Employee e : employees){
                writer.writeNext(new String[]{
                    e.getEmployeeID(),
                    e.getName(),
                    e.getRole(),
                    e.getPassword(),
                    e.getOutletCode()
                });
            }

            System.out.println("Employees saved successfully!");

        } catch (IOException ex){
            System.err.println("Error saving employees: " + ex.getMessage());
        }
    }
    //SAVE MODELS
    public static void saveModels(List<Model> models, List<Outlet> outlets) {
        
        try (CSVWriter writer = new CSVWriter(new FileWriter("data/model.csv"))) {

            //1. Create header : Model, Price, C60, C61, ...
            String[] header = new String[outlets.size() + 2];
            header[0] = "Model";
            header[1] = "Price";

            for(int i = 0; i < outlets.size(); i++) {
                header[i + 2] = outlets.get(i).getOutletCode();
            }

            writer.writeNext(header);

            //2. Write model rows
            for (Model model : models) {
                String[] row = new String[outlets.size() + 2];

                row[0] = model.getModelId();
                row[1] = String.valueOf(model.getPrice());

                for (int i = 0; i < outlets.size(); i++) {
                    String outletCode = outlets.get(i).getOutletCode();
                    row[i + 2] = String.valueOf(
                        model.getStockByOutlet().getOrDefault(outletCode, 0)
                    );
                }

                writer.writeNext(row);
            }

            System.out.println("Model data saved to model.csv");


        } catch (IOException e){
            System.out.println("Error saving model data: " + e.getMessage());
        }
        
    }
}

