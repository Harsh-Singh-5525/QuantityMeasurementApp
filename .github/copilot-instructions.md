# Quantity Measurement App - AI Development Guide

## Project Overview
This is a unit conversion and quantity comparison engine using a **DRY (Don't Repeat Yourself) pattern** with generic types. The core concept: normalize all lengths to a **base unit (inches)** for comparison, enabling seamless equality checks across different units.

### Key Architecture
- **Single generic `Length` class** in [src/main/java/com/apps/quantitymeasurement/QuantityMeasurementApp.java](src/main/java/com/apps/quantitymeasurement/QuantityMeasurementApp.java) that replaces individual unit classes
- **`LengthUnit` enum** stores unit-specific conversion factors (one source of truth)
- **Base unit pattern**: All comparisons convert values to inches via `convertToBaseUnit()` method
- **Value-based equality**: Custom `equals()` method compares converted values using `Double.compare()` for floating-point precision

## Critical Patterns & Implementation Rules

### 1. Adding New Length Units
When extending `LengthUnit` enum, **always provide the conversion factor to inches**:
```java
MILLIMETERS(0.0393701);  // 1 mm = 0.0393701 inches
```
This single entry automatically enables conversion with all existing units—no additional code needed.

### 2. Equality Logic
The `Length.equals()` method follows a 3-step pattern (see lines 48-62):
1. **Reference check**: `if (this == obj) return true;` (fast path)
2. **Null & type check**: `if (obj == null || this.getClass() != obj.getClass()) return false;`
3. **Value comparison**: `Double.compare(this.convertToBaseUnit(), other.convertToBaseUnit()) == 0;`

This pattern handles null safely and compares using converted base units. **Never compare raw values directly.**

### 3. Exception Handling
Unit validation is strict: `null` units throw `IllegalArgumentException` immediately in the constructor. This fails-fast approach prevents silent bugs with invalid quantities.

## Build & Test Workflow

### Maven Configuration
- **Java version**: 22 (compiler properties) but Maven plugin targets 11 for compatibility
- **Testing framework**: JUnit 5 (Jupiter) with Surefire plugin
- **Build command**: `mvn clean compile`
- **Test command**: `mvn test`

### Test Structure
Tests are organized by equality scenarios in [src/test/java/com/apps/quantitymeasurement/QuantityMeasurementAppTest.java](src/test/java/com/apps/quantitymeasurement/QuantityMeasurementAppTest.java):
- **Unit equality** (same unit, different values)
- **Cross-unit equivalence** (yards = feet = inches)
- **Edge cases** (null units, null comparisons, same references)
- **Transitivity** (if A=B and B=C, then A=C)

**Test naming convention**: `testEquality_[Unit1]To[Unit2]_[Scenario]` for immediate clarity of what's being tested.

## Development Checklist for Extensions

When adding features:
- ✓ Add unit to `LengthUnit` enum with correct conversion factor
- ✓ Run `mvn test` to verify no regressions in equality comparisons
- ✓ Add test cases covering new unit with existing units (cross-unit scenarios)
- ✓ Verify floating-point precision isn't an issue for new conversions (use `Double.compare()`)
- ✓ Keep `Length` class immutable (no setters for value/unit)

## Key Files
- **Main logic**: [src/main/java/com/apps/quantitymeasurement/QuantityMeasurementApp.java](src/main/java/com/apps/quantitymeasurement/QuantityMeasurementApp.java)
- **Tests**: [src/test/java/com/apps/quantitymeasurement/QuantityMeasurementAppTest.java](src/test/java/com/apps/quantitymeasurement/QuantityMeasurementAppTest.java)
- **Build config**: [pom.xml](pom.xml)

## Common Tasks

| Task | Command |
|------|---------|
| Compile | `mvn clean compile` |
| Run tests | `mvn test` |
| Run app | `mvn exec:java -Dexec.mainClass="com.apps.quantitymeasurement.QuantityMeasurementApp"` |
| Debug a unit | Add conversion factor to `LengthUnit` enum, then add test case |
