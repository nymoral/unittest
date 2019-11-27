## Exercises

Most classes in this project are incorrect.
They compile fine, but fail to do what's described in their documentation comments
or crash at runtime.

Your task is to write unit tests for each method and find failing cases.
Once incorrectly handled input is found, improve the method until failure is fixed.

### Exercise I

All methods in `lt.mif.unit.exercise1` package classes are static. Test them with various inputs:

* Write an assertion for a typical use case.
* Write an assertion for empty or `null` input.
* Write an assertion with very large inputs.

When a test failure is found, open the implementation and try to fix the problem.

### Exercise II

`lt.mif.unit.exercise2` package contains only a single class. This class is dependant on external dependency - a database implementation.

When testing the `UserListController` object inject it with a mocked implementation describing the desired behaviour (see an example in `UserListControllerTest`).
