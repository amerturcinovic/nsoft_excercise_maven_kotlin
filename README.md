# turcinovic coding excercise Live Score Board

## Simple library implementation for Score Board of World Cup with Maven (Kotlin)

## Interface / API has 4 methods

- Add or start match - start new match
    - Exception `IllegalArgumentException` is thrown if arguments are wrong or match already started
- Finish match in progress - finish match in progress
    - Exception IllegalArgumentException` is thrown if arguments are wrong or if there is no match on score board
- Update match with new absolute score - update match score result
-   - Exception `IllegalArgumentException` is thrown if arguments are wrong
- Get score board ranking - get all match in progress ordered by most scores of both teams or most recent match

## Installation

Instructions on how to compile and create JAR file.

### Clone the repository
```bash
git clone https://github.com/amerturcinovic/turcinovic_coding_excercise_v_1_11.git
```

### Navigate to the project directory
```bash
cd fwcscoreboardlib
```
### Run maven command
```bash
mvn clean install
```
This will create `*.class` file in `./target` directory and also run all unit tests

You can also run `mvn test` to run all tests

### Create *.jar files - package lib
```bash
mvn clean package
```
This will `fwc-score-board-lib-1.0-SNAPSHOT.jar` in `./out` directory


## Example usage
### Default implementation
```java
class Scratch {
    public static void main(String[] args) {
        TrackableScoreBoard scoreBoard = new FootballWorldCupScoreBoard();
        scoreBoard.startMatch("BRAZIL", "ARGENTINA");
        scoreBoard.updateMatch(
                new MatchInfo("BRAZIL", 1, "ARGENTINA", 0)
        );
        List<MatchInfo> boardRanking = scoreBoard.getBoardRanking();
    }
}
```
### Implementation with custom repository for score board
```java
class Scratch {
    public static void main(String[] args) {
        TrackableScoreBoard scoreBoard = new FootballWorldCupScoreBoard(
                new SimpleOrderedInMemoryCollection()
        );
        scoreBoard.startMatch("BRAZIL", "ARGENTINA");
        List<MatchInfo> boardRanking = scoreBoard.getBoardRanking(); // to get list of matches
        System.out.println(boardRanking); // will call .toString() and return as: 1. BRAZIL 0 - ARGENTINA 0 
    }
}
```

For more details on example usage see: [Example usage](fwcscoreboardlib/src/main/kotlin/org/nsoft/exercise/scoreboard/example/App.kt)

### Notes and information for project
- Implemented as simple maven library project in Java
- Implementation is covered by test cases
- You have example of usage in `./example` folder
- It is very simple to change sorting of score board or persistence of score board,
  as everything is modular and changeable and easy to change with different implementation,
  just implement interfaces `Repository` and `Sortable` to have different implementation
- But we could also create this without interface just pure one implementation of in memory collection
  but for easy change I  have added this with interface
- Default implementation use ordered `Collection LinkedHashMap`
- But it is easy to change to some concurrent thread safe implementation if we want
- Minimum data are stored in memory only teams name and scores
  we could also design interface / API that provide exact start of match
  and that could be also one of sorting criteria
- We could have start and end timestamp of match and much more details in repository\
  but it is not requirements of the task to show start and end of match