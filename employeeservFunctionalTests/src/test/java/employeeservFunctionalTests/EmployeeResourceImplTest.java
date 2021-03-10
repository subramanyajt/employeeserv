package employeeservFunctionalTests;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.util.InputValidator;



@RunWith(SpringRunner.class)
@WebMvcTest(value = EmployeeResourceImpl.class)
public class EmployeeResourceImplTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployeeService employeeService;
	
	@MockBean
    private InputValidator inputValidator;
	
	@Test
	public void employeeGetByIdTest()  {
		
		Employee employee = Utility.getEmployee();

		Mockito.when(
				employeeService.getById(Mockito.anyString()).thenReturn(employee));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/v1/bfs/employees/1").accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		
		JSONAssert.assertEquals(Utility.getResult(), result.getResponse()
				.getContentAsString(), false);
	}
	
	@Test
	public void createEmployeeTest() throws Exception {
		Employee employee = Utility.getEmployee();

		Mockito.when(
				employeeService.getById(Mockito.anyString()).thenReturn(employee));
				
		Mockito.when(
				inputValidator.getErrors(Mockito.any(Employee.class)).thenReturn(Optional.empty()));

		// employeeService.create to respond with boolean value
		Mockito.when(
				employeeService.createEmployee(Mockito.any(Employee.class))).thenReturn(true);

		// Send employee as body to /v1/bfs/employee
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/v1/bfs/employee")
				.accept(MediaType.APPLICATION_JSON).content(Utility.getResult())
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());

	}

}
