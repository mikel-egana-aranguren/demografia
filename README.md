## Usage

### Leiningen

lein grafter run demografia.core/convert-persons-data ./data/example-data.csv output.csv
lein grafter run demografia.core/convert-persons-data ./data/example-data.csv output.xlsx
lein grafter run demografia.core/convert-persons-data-to-graph ./data/example-data.csv ./output/output.ttl

### REPL

=> (demografia.core/convert-persons-data-to-graph "./data/example-data.csv")


