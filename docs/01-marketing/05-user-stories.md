# User Stories

## 1. Finding units of measurement for a user task

**As** a user of the service, \
**I want** to see possible units for my task, \
**To be able** to choose the most suitable options.

1. Scenario: adding/editing a product. \
   **Given:** I have a product. \
   **When** I go to the add/edit product page, \
   **then** I see a field with an alphabetically sorted list of possible units of measurement, \
   **and** the list can be searched.

## 2. Adding units of measure

**As** a user of the service, \
**I want** to be able to add a unit of measure, \
**When** I don't find what I need.

1. Scenario: adding/editing a product. \
   **Given:** there is a product for which you need to specify a unit of measure. \
   **When** the proposed list does not contain a suitable unit of measure, \
   **then** it is possible to add a new one by specifying the name and unit of measure.
