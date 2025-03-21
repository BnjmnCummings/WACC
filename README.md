# WACC Compiler

This project is an end-to-end compiler for the **While-like Academy Compiler Construction** (WACC) programming language. The compiler translates WACC code into x86-64(intel syntax) assembly, enabling execution on x86-based architectures.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Building the Compiler](#building-the-compiler)
- [Running the Compiler](#running-the-compiler)
- [Project Structure](#project-structure)
- [Testing](#testing)

## Introduction

WACC is a teaching language used at Imperial College London, based on the While family of languages. This compiler project was developed as part of the second-year curriculum to provide hands-on experience in compiler construction.

## Features

- **Lexical Analysis**: Tokenizes WACC source code.
- **Syntax Analysis**: Parses tokens to verify grammatical structure.
- **Semantic Analysis**: Ensures semantic correctness, including type checking.
- **Code Generation**: Produces x86 assembly code from WACC source.
- **Error Handling**: Provides meaningful error messages for syntax and semantic errors.

## Prerequisites

- **Scala**: Ensure Scala is installed on your system.
- **x86 Architecture/Emulator**: This project compiles .wacc programs to x86 assembly. Hence, you won't be able to run programs on ARM architecture (and neither will the integration tests).

## Running the Compiler
- **Using Main.scala**: You can run programs through the [Main.scala](https://github.com/BnjmnCummings/WACC/blob/main/src/main/wacc/Main.scala) pipeline by using scala-cli:
    ```bash
    scala run . --"<PATH-TO-WACC-PROGRAM>"
    ```
## Project Structure
- **`src/main/`**: Contains the source code for the compiler, organized into various subdirectories based on functionality:

    - **`frontend/`**: Includes code responsible for parsing .wacc programs into an Abstract Syntax Tree
        - **`syntax/`**: Code handling the syntactical analysis of .wacc programs.
        - **`semantic/`**: Code handling the semantic analysis of .wacc programs, including scope and type checking.
        - **`error/`**: Error handling and formatting libraries for the front-end.

    - **`backend/`**: Includes code responsible for generating assembly code based on the internal representation generated by the front-end.
        - **`assemblyIR/`**: Classes/traits for an internal representation tree that represents an assembly program.
        - **`codeGen/`**: Code handling the conversion from an Abstract Syntax Tree to the AssemblyIR.
        - **`formatting/`**: Code handling the conversion from Internal Representation to assembly files.

- **`src/test/`**: Unit and integration tests for the frontend and backend.

- **`wacc-examples/`**: Provides example WACC programs for testing and demonstration purposes.


## Testing
- **Using testSettings.scala**: You can comment / uncomment specific test cases in [testSettings.scala](https://github.com/BnjmnCummings/WACC/blob/main/src/test/wacc/testUtils/testSettings.scala). Then you can run:
    ```bash
    scala test .
    ```
    Commented test cases will be marked as 'pending'.

- **Using a pattern matcher**: You can alternatively run specific tests by specifying a pattern to look for in the test paths.To do this use the [--test-only](https://scala-cli.virtuslab.org/docs/commands/test/#filter-test-suite) flag followed by a pattern.
    ```bash
    scala test . --test-only "<PATTERN>"
    ```
    For example, to run myTest.scala, this is sufficient:
    ```bash
    scala test . --test-only "*myTest*"
    ```