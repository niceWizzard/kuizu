package com.coderizzard.network.data.util.data

import com.coderizzard.network.data.QuizJsonDeserializer
import com.google.gson.Gson
import com.google.gson.JsonElement
import org.junit.Assert
import org.junit.Test

internal class TestQuizJsonDeserializer {
    private val jsonData : JsonElement = Gson().fromJson(
        """
            {
              "success": true,
              "message": "Ok",
              "data": {
                "quiz": {
                  "isTagged": false,
                  "isLoved": false,
                  "tagIds": [],
                  "stats": {
                    "played": 0,
                    "totalPlayers": 0,
                    "totalCorrect": 0,
                    "totalQuestions": 0
                  },
                  "love": 0,
                  "cloned": false,
                  "parentDetail": null,
                  "deleted": false,
                  "archived": false,
                  "lifetimeAccess": false,
                  "draftVersion": null,
                  "publishedVersion": "6718ab862badf7e534a0c2cc",
                  "isShared": false,
                  "type": "quiz",
                  "_id": "66f93cdf2ea35985eb870319",
                  "createdBy": {
                    "local": {
                      "username": "richard232manansala_98976",
                      "casedUsername": "richard232manansala_98976"
                    },
                    "google": {
                      "createdAt": "2020-09-03T02:25:46.084Z",
                      "profileId": "110325712778295481949",
                      "displayName": "Richard Manansala",
                      "firstName": "Richard",
                      "lastName": "Manansala",
                      "image": "https://lh3.googleusercontent.com/a/ACg8ocJuHkes6O6HLUCm7ata2thGG3j81Q1jzfbYOyp3bMVvue_bcVtK=s96-c"
                    },
                    "gameChangerBadge": {
                      "isEligible": false,
                      "showOnProfile": false
                    },
                    "student": {
                      "dob": "2000-03-01T00:00:00.000Z",
                      "underAge": false
                    },
                    "deactivated": false,
                    "deleted": false,
                    "_id": "5f50542a2938a0001c86578b",
                    "country": "PH",
                    "occupation": "student",
                    "media": "https://lh3.googleusercontent.com/a/ACg8ocJuHkes6O6HLUCm7ata2thGG3j81Q1jzfbYOyp3bMVvue_bcVtK=s96-c",
                    "firstName": "Richard",
                    "lastName": "Manansala",
                    "id": "5f50542a2938a0001c86578b"
                  },
                  "info": {
                    "lang": "English",
                    "aiGenerated": false,
                    "qm": {
                      "66f93cf525bde85c3bad0661": {
                        "time": 30000
                      },
                      "66f93cfa73d0be091bd8d5d2": {
                        "time": 45000
                      },
                      "66f93d1a0d56cbc85e2c3d56": {
                        "time": 60000
                      },
                      "66f93d1ee570c8ad80be7c58": {
                        "time": 60000
                      },
                      "66f93d64903ac75cf291b171": {
                        "time": 60000
                      },
                      "66f93ee9e570c877ebbe7c91": {
                        "time": 60000
                      },
                      "6718aba44aa76ced51ccecb2": {
                        "time": 60000
                      },
                      "6718aba9bf9bde6e581976e1": {
                        "time": 60000
                      }
                    },
                    "visibility": true,
                    "subjects": [
                      "Specialty"
                    ],
                    "topics": [],
                    "subtopics": [],
                    "grade": [
                      "13",
                      "13"
                    ],
                    "gradeLevel": null,
                    "deleted": false,
                    "pref": {
                      "time": null
                    },
                    "traits": {
                      "isQuizWithoutCorrectAnswer": false,
                      "totalSlides": 0,
                      "totalComprehensionQuestions": 0,
                      "nonAnswerableQuestions": 0
                    },
                    "standardIds": [],
                    "__v": 3,
                    "meta": {
                      "slot": 7,
                      "experiment": "main_main"
                    },
                    "_id": "6718ab862badf7e534a0c2cc",
                    "name": "Quizziz API ",
                    "image": "",
                    "questions": [
                      {
                        "structure": {
                          "settings": {
                            "hasCorrectAnswer": true,
                            "fibDataType": "string",
                            "canSubmitCustomResponse": false,
                            "doesOptionHaveMultipleTargets": false
                          },
                          "hasMath": false,
                          "query": {
                            "type": null,
                            "hasMath": false,
                            "math": {
                              "latex": [],
                              "template": null
                            },
                            "answerTime": -1,
                            "text": "\u003Cp\u003EThis is a multiple choice\u003C/p\u003E",
                            "media": []
                          },
                          "options": [
                            {
                              "type": "text",
                              "hasMath": false,
                              "math": {
                                "latex": [],
                                "template": null
                              },
                              "answerTime": 0,
                              "id": "66f93cdc87ed49ef2beeedd3",
                              "text": "\u003Cp\u003EA\u003C/p\u003E",
                              "media": [],
                              "_id": "66f93cdc87ed49ef2beeedd3"
                            },
                            {
                              "type": "text",
                              "hasMath": false,
                              "math": {
                                "latex": [],
                                "template": null
                              },
                              "answerTime": 0,
                              "id": "66f93cdc87ed49ef2beeedd4",
                              "text": "\u003Cp\u003EB\u003C/p\u003E",
                              "media": [],
                              "_id": "66f93cdc87ed49ef2beeedd4"
                            },
                            {
                              "type": "text",
                              "hasMath": false,
                              "math": {
                                "latex": [],
                                "template": null
                              },
                              "answerTime": 0,
                              "id": "66f93cdc87ed49ef2beeedd5",
                              "text": "\u003Cp\u003EC\u003C/p\u003E",
                              "media": [],
                              "_id": "66f93cdc87ed49ef2beeedd5"
                            },
                            {
                              "type": "text",
                              "hasMath": false,
                              "math": {
                                "latex": [],
                                "template": null
                              },
                              "answerTime": 0,
                              "id": "66f93cdc87ed49ef2beeedd6",
                              "text": "\u003Cp\u003ED\u003C/p\u003E",
                              "media": [],
                              "_id": "66f93cdc87ed49ef2beeedd6"
                            },
                            {
                              "type": "text",
                              "hasMath": false,
                              "math": {
                                "latex": [],
                                "template": null
                              },
                              "answerTime": 0,
                              "id": "66f93ce787ed49ef2beeedea",
                              "text": "\u003Cp\u003EE\u003C/p\u003E",
                              "media": [],
                              "_id": "66f93ce787ed49ef2beeedea"
                            }
                          ],
                          "explain": {
                            "type": "",
                            "text": "",
                            "media": [],
                            "hasMath": false,
                            "math": {
                              "template": null,
                              "latex": []
                            },
                            "answerTime": 0
                          },
                          "hints": [],
                          "kind": "MCQ",
                          "theme": {
                            "fontFamily": "Quicksand",
                            "titleFontFamily": "Quicksand",
                            "fontColor": {
                              "text": "#5D2057"
                            },
                            "background": {
                              "color": "#FFFFFF",
                              "image": "",
                              "video": ""
                            },
                            "shape": {
                              "largeShapeColor": "#F2F2F2",
                              "smallShapeColor": "#9A4292"
                            }
                          },
                          "marks": {
                            "correct": 1,
                            "incorrect": 0
                          },
                          "answer": 0
                        },
                        "standards": [],
                        "topics": [],
                        "isSuperParent": false,
                        "standardIds": [],
                        "aiGenerated": false,
                        "__v": 2,
                        "teleportFrom": null,
                        "cloned": false,
                        "ver": 2,
                        "parent": null,
                        "_id": "66f93cf525bde85c3bad0661",
                        "time": 30000,
                        "published": true,
                        "aiCreateMeta": null,
                        "type": "MCQ",
                        "createdAt": "2024-09-29T11:41:41.981Z",
                        "updated": "2024-09-29T11:50:10.522Z",
                        "cached": true
                      },
                      {
                        "structure": {
                          "settings": {
                            "hasCorrectAnswer": true,
                            "fibDataType": "string",
                            "canSubmitCustomResponse": false,
                            "doesOptionHaveMultipleTargets": false
                          },
                          "hasMath": false,
                          "query": {
                            "type": null,
                            "hasMath": false,
                            "math": {
                              "latex": [],
                              "template": null
                            },
                            "answerTime": -1,
                            "text": "\u003Cp\u003EThis is a multiple choice with multiple correct answers\u003C/p\u003E",
                            "media": []
                          },
                          "options": [
                            {
                              "type": "text",
                              "hasMath": false,
                              "math": {
                                "latex": [],
                                "template": null
                              },
                              "answerTime": 0,
                              "id": "66f93cdc87ed49ef2beeedd3",
                              "text": "\u003Cp\u003EA\u003C/p\u003E",
                              "media": [],
                              "_id": "66f93cdc87ed49ef2beeedd3"
                            },
                            {
                              "type": "text",
                              "hasMath": false,
                              "math": {
                                "latex": [],
                                "template": null
                              },
                              "answerTime": 0,
                              "id": "66f93cdc87ed49ef2beeedd4",
                              "text": "\u003Cp\u003EB\u003C/p\u003E",
                              "media": [],
                              "_id": "66f93cdc87ed49ef2beeedd4"
                            },
                            {
                              "type": "text",
                              "hasMath": false,
                              "math": {
                                "latex": [],
                                "template": null
                              },
                              "answerTime": 0,
                              "id": "66f93cdc87ed49ef2beeedd5",
                              "text": "\u003Cp\u003EC\u003C/p\u003E",
                              "media": [],
                              "_id": "66f93cdc87ed49ef2beeedd5"
                            },
                            {
                              "type": "text",
                              "hasMath": false,
                              "math": {
                                "latex": [],
                                "template": null
                              },
                              "answerTime": 0,
                              "id": "66f93cdc87ed49ef2beeedd6",
                              "text": "\u003Cp\u003ED\u003C/p\u003E",
                              "media": [],
                              "_id": "66f93cdc87ed49ef2beeedd6"
                            },
                            {
                              "type": "text",
                              "hasMath": false,
                              "math": {
                                "latex": [],
                                "template": null
                              },
                              "answerTime": 0,
                              "id": "66f93ce787ed49ef2beeedea",
                              "text": "\u003Cp\u003EE\u003C/p\u003E",
                              "media": [],
                              "_id": "66f93ce787ed49ef2beeedea"
                            }
                          ],
                          "explain": {
                            "type": "",
                            "text": "",
                            "media": [],
                            "hasMath": false,
                            "math": {
                              "template": null,
                              "latex": []
                            },
                            "answerTime": 0
                          },
                          "hints": [],
                          "kind": "MSQ",
                          "theme": {
                            "fontFamily": "Quicksand",
                            "titleFontFamily": "Quicksand",
                            "fontColor": {
                              "text": "#5D2057"
                            },
                            "background": {
                              "color": "#FFFFFF",
                              "image": "",
                              "video": ""
                            },
                            "shape": {
                              "largeShapeColor": "#F2F2F2",
                              "smallShapeColor": "#9A4292"
                            }
                          },
                          "marks": {
                            "correct": 1,
                            "incorrect": 0
                          },
                          "answer": [0, 1]
                        },
                        "standards": [],
                        "topics": [],
                        "isSuperParent": false,
                        "standardIds": [],
                        "aiGenerated": false,
                        "__v": 3,
                        "teleportFrom": null,
                        "cloned": false,
                        "ver": 2,
                        "parent": null,
                        "_id": "66f93cfa73d0be091bd8d5d2",
                        "time": 45000,
                        "published": true,
                        "aiCreateMeta": null,
                        "type": "MSQ",
                        "createdAt": "2024-09-29T11:41:46.832Z",
                        "updated": "2024-09-29T11:50:10.522Z",
                        "cached": true
                      },
                      {
                        "structure": {
                          "settings": {
                            "hasCorrectAnswer": true,
                            "fibDataType": "string",
                            "canSubmitCustomResponse": false,
                            "doesOptionHaveMultipleTargets": false,
                            "ignoreAccentMarksForEvaluation": false,
                            "enableAccentMarks": false,
                            "questionMetadata": {
                              "specialIndices": [],
                              "answerLength": 4
                            },
                            "questionDisplayVariant": "single_blank"
                          },
                          "hasMath": false,
                          "query": {
                            "type": null,
                            "hasMath": false,
                            "math": {
                              "latex": [],
                              "template": null
                            },
                            "answerTime": -1,
                            "text": "\u003Cp\u003EThis is a fill in the blank\u003C/p\u003E",
                            "media": []
                          },
                          "options": [
                            {
                              "type": "text",
                              "hasMath": false,
                              "math": {
                                "latex": [],
                                "template": null
                              },
                              "answerTime": 0,
                              "id": "66f93d0487ed49ef2beeee1c",
                              "text": "nice",
                              "media": [],
                              "_id": "66f93d0487ed49ef2beeee1c"
                            }
                          ],
                          "explain": {
                            "type": "",
                            "text": "",
                            "media": [],
                            "hasMath": false,
                            "math": {
                              "template": null,
                              "latex": []
                            },
                            "answerTime": 0
                          },
                          "hints": [],
                          "kind": "BLANK",
                          "theme": {
                            "fontFamily": "Quicksand",
                            "titleFontFamily": "Quicksand",
                            "fontColor": {
                              "text": "#5D2057"
                            },
                            "background": {
                              "color": "#FFFFFF",
                              "image": "",
                              "video": ""
                            },
                            "shape": {
                              "largeShapeColor": "#F2F2F2",
                              "smallShapeColor": "#9A4292"
                            }
                          },
                          "marks": {
                            "correct": 1,
                            "incorrect": 0
                          },
                          "answer": 0
                        },
                        "standards": [],
                        "topics": [],
                        "isSuperParent": false,
                        "standardIds": [],
                        "aiGenerated": false,
                        "__v": 2,
                        "teleportFrom": null,
                        "cloned": false,
                        "ver": 4,
                        "parent": null,
                        "_id": "66f93d1a0d56cbc85e2c3d56",
                        "time": 60000,
                        "published": true,
                        "aiCreateMeta": null,
                        "type": "BLANK",
                        "createdAt": "2024-09-29T11:42:18.695Z",
                        "updated": "2024-09-29T11:50:10.522Z",
                        "cached": true
                      },
                      {
                        "structure": {
                          "settings": {
                            "hasCorrectAnswer": true,
                            "fibDataType": "string",
                            "canSubmitCustomResponse": false,
                            "doesOptionHaveMultipleTargets": false,
                            "ignoreAccentMarksForEvaluation": false,
                            "enableAccentMarks": false,
                            "questionMetadata": {
                              "specialIndices": [],
                              "answerLength": 4
                            },
                            "questionDisplayVariant": "single_blank"
                          },
                          "hasMath": false,
                          "query": {
                            "type": null,
                            "hasMath": false,
                            "math": {
                              "latex": [],
                              "template": null
                            },
                            "answerTime": -1,
                            "text": "\u003Cp\u003EThis is a fill in the blank with alternative answers\u003C/p\u003E",
                            "media": []
                          },
                          "options": [
                            {
                              "type": "text",
                              "hasMath": false,
                              "math": {
                                "latex": [],
                                "template": null
                              },
                              "answerTime": 0,
                              "id": "66f93d0487ed49ef2beeee1c",
                              "text": "nice",
                              "media": [],
                              "_id": "66f93d0487ed49ef2beeee1c"
                            },
                            {
                              "type": "text",
                              "hasMath": false,
                              "math": {
                                "latex": [],
                                "template": null
                              },
                              "answerTime": 0,
                              "id": "66f93d1987ed49ef2beeee2a",
                              "text": "exactly",
                              "matcher": "exact",
                              "media": [],
                              "_id": "66f93d1987ed49ef2beeee2a"
                            },
                            {
                              "type": "text",
                              "hasMath": false,
                              "math": {
                                "latex": [],
                                "template": null
                              },
                              "answerTime": 0,
                              "id": "66f93d2787ed49ef2beeee2b",
                              "text": "contains",
                              "matcher": "contains",
                              "media": [],
                              "_id": "66f93d2787ed49ef2beeee2b"
                            }
                          ],
                          "explain": {
                            "type": "",
                            "text": "",
                            "media": [],
                            "hasMath": false,
                            "math": {
                              "template": null,
                              "latex": []
                            },
                            "answerTime": 0
                          },
                          "hints": [
                            {
                              "type": "text",
                              "text": "",
                              "media": [],
                              "hasMath": false,
                              "math": {
                                "template": null,
                                "latex": []
                              },
                              "answerTime": 0,
                              "_id": "66f93d1987ed49ef2beeee29",
                              "id": "66f93d1987ed49ef2beeee29"
                            }
                          ],
                          "kind": "BLANK",
                          "theme": {
                            "fontFamily": "Quicksand",
                            "titleFontFamily": "Quicksand",
                            "fontColor": {
                              "text": "#5D2057"
                            },
                            "background": {
                              "color": "#FFFFFF",
                              "image": "",
                              "video": ""
                            },
                            "shape": {
                              "largeShapeColor": "#F2F2F2",
                              "smallShapeColor": "#9A4292"
                            }
                          },
                          "marks": {
                            "correct": 1,
                            "incorrect": 0
                          },
                          "answer": 0
                        },
                        "standards": [],
                        "topics": [],
                        "isSuperParent": false,
                        "standardIds": [],
                        "aiGenerated": false,
                        "__v": 3,
                        "teleportFrom": null,
                        "cloned": false,
                        "ver": 4,
                        "parent": null,
                        "_id": "66f93d1ee570c8ad80be7c58",
                        "time": 60000,
                        "published": true,
                        "aiCreateMeta": null,
                        "type": "BLANK",
                        "createdAt": "2024-09-29T11:42:22.808Z",
                        "updated": "2024-09-29T11:50:10.522Z",
                        "cached": true
                      },
                      {
                        "structure": {
                          "settings": {
                            "hasCorrectAnswer": true,
                            "fibDataType": "string",
                            "canSubmitCustomResponse": false,
                            "doesOptionHaveMultipleTargets": false,
                            "ignoreAccentMarksForEvaluation": true,
                            "enableAccentMarks": false,
                            "questionMetadata": {
                              "specialIndices": [],
                              "answerLength": 6
                            },
                            "questionDisplayVariant": "single_blank"
                          },
                          "hasMath": false,
                          "query": {
                            "type": null,
                            "hasMath": false,
                            "math": {
                              "latex": [],
                              "template": null
                            },
                            "answerTime": -1,
                            "text": "\u003Cp\u003EThis question has an answer explanation and a hint\u003C/p\u003E",
                            "media": []
                          },
                          "options": [
                            {
                              "type": "text",
                              "hasMath": false,
                              "math": {
                                "latex": [],
                                "template": null
                              },
                              "answerTime": 0,
                              "id": "66f93d4087ed49ef2beeee40",
                              "text": "answer",
                              "media": [],
                              "_id": "66f93d4087ed49ef2beeee40"
                            }
                          ],
                          "explain": {
                            "type": "text",
                            "text": "\u003Cp\u003EExplanation is something\u003C/p\u003E",
                            "media": [],
                            "hasMath": false,
                            "math": {
                              "template": null,
                              "latex": []
                            },
                            "answerTime": 0
                          },
                          "hints": [
                            {
                              "type": "text",
                              "text": "hint",
                              "media": [],
                              "hasMath": false,
                              "math": {
                                "template": null,
                                "latex": []
                              },
                              "answerTime": 0,
                              "_id": "66f93d5887ed49ef2beeee4d",
                              "id": "66f93d5887ed49ef2beeee4d"
                            }
                          ],
                          "kind": "BLANK",
                          "theme": {
                            "fontFamily": "Quicksand",
                            "titleFontFamily": "Quicksand",
                            "fontColor": {
                              "text": "#5D2057"
                            },
                            "background": {
                              "color": "#FFFFFF",
                              "image": "",
                              "video": ""
                            },
                            "shape": {
                              "largeShapeColor": "#F2F2F2",
                              "smallShapeColor": "#9A4292"
                            }
                          },
                          "marks": {
                            "correct": 1,
                            "incorrect": 0
                          },
                          "answer": 0
                        },
                        "standards": [],
                        "topics": [],
                        "isSuperParent": false,
                        "standardIds": [],
                        "aiGenerated": false,
                        "__v": 2,
                        "teleportFrom": null,
                        "cloned": false,
                        "ver": 4,
                        "parent": null,
                        "_id": "66f93d64903ac75cf291b171",
                        "time": 60000,
                        "published": true,
                        "aiCreateMeta": null,
                        "type": "BLANK",
                        "createdAt": "2024-09-29T11:43:32.112Z",
                        "updated": "2024-09-29T11:50:10.522Z",
                        "cached": true
                      },
                      {
                        "structure": {
                          "settings": {
                            "hasCorrectAnswer": true,
                            "fibDataType": "string",
                            "canSubmitCustomResponse": false,
                            "doesOptionHaveMultipleTargets": false,
                            "ignoreAccentMarksForEvaluation": true,
                            "enableAccentMarks": false,
                            "questionMetadata": {
                              "specialIndices": [],
                              "answerLength": 6
                            },
                            "questionDisplayVariant": "box"
                          },
                          "hasMath": false,
                          "query": {
                            "type": null,
                            "hasMath": false,
                            "math": {
                              "latex": [],
                              "template": null
                            },
                            "answerTime": -1,
                            "text": "\u003Cp\u003EThis is a fill in the blank with seperate boxes\u003C/p\u003E",
                            "media": []
                          },
                          "options": [
                            {
                              "type": "text",
                              "hasMath": false,
                              "math": {
                                "latex": [],
                                "template": null
                              },
                              "answerTime": 0,
                              "id": "66f93ecd87ed49ef2beeee6c",
                              "text": "answer",
                              "media": [],
                              "_id": "66f93ecd87ed49ef2beeee6c"
                            }
                          ],
                          "explain": {
                            "type": "",
                            "text": "",
                            "media": [],
                            "hasMath": false,
                            "math": {
                              "template": null,
                              "latex": []
                            },
                            "answerTime": 0
                          },
                          "hints": [
                            {
                              "type": "text",
                              "text": "",
                              "media": [],
                              "hasMath": false,
                              "math": {
                                "template": null,
                                "latex": []
                              },
                              "answerTime": 0,
                              "_id": "66f93ed287ed49ef2beeee77",
                              "id": "66f93ed287ed49ef2beeee77"
                            }
                          ],
                          "kind": "BLANK",
                          "theme": {
                            "fontFamily": "Quicksand",
                            "titleFontFamily": "Quicksand",
                            "fontColor": {
                              "text": "#5D2057"
                            },
                            "background": {
                              "color": "#FFFFFF",
                              "image": "",
                              "video": ""
                            },
                            "shape": {
                              "largeShapeColor": "#F2F2F2",
                              "smallShapeColor": "#9A4292"
                            }
                          },
                          "marks": {
                            "correct": 1,
                            "incorrect": 0
                          },
                          "answer": 0
                        },
                        "standards": [],
                        "topics": [],
                        "isSuperParent": false,
                        "standardIds": [],
                        "aiGenerated": false,
                        "__v": 1,
                        "teleportFrom": null,
                        "cloned": false,
                        "ver": 4,
                        "parent": null,
                        "_id": "66f93ee9e570c877ebbe7c91",
                        "time": 60000,
                        "published": true,
                        "aiCreateMeta": null,
                        "type": "BLANK",
                        "createdAt": "2024-09-29T11:50:01.294Z",
                        "updated": "2024-09-29T11:50:10.522Z",
                        "cached": true
                      },
                      {
                        "structure": {
                          "settings": {
                            "hasCorrectAnswer": true,
                            "fibDataType": "string",
                            "canSubmitCustomResponse": false,
                            "doesOptionHaveMultipleTargets": false,
                            "ignoreAccentMarksForEvaluation": false,
                            "enableAccentMarks": false,
                            "questionMetadata": {
                              "specialIndices": [],
                              "answerLength": 4
                            },
                            "questionDisplayVariant": "single_blank"
                          },
                          "hasMath": false,
                          "query": {
                            "type": null,
                            "hasMath": false,
                            "math": {
                              "latex": [],
                              "template": null
                            },
                            "answerTime": -1,
                            "text": "\u003Cp\u003EThis is a question with an image\u003C/p\u003E",
                            "media": [
                              {
                                "type": "image",
                                "url": "https://media.quizizz.com/_mdserver/main/media/resource/gs/quizizz-media/quizzes/7d9dc490-dee9-4a40-9b48-c2323025f9e8-v2",
                                "meta": {
                                  "width": 0,
                                  "height": 0,
                                  "text": "",
                                  "bgColor": "",
                                  "layout": "",
                                  "videoId": "",
                                  "start": 0,
                                  "end": 0,
                                  "duration": 0,
                                  "kind": "",
                                  "embeddable": true,
                                  "title": ""
                                }
                              }
                            ]
                          },
                          "options": [
                            {
                              "type": "text",
                              "hasMath": false,
                              "math": {
                                "latex": [],
                                "template": null
                              },
                              "answerTime": 0,
                              "id": "6718ab859a5e7251f6d67e43",
                              "text": "true",
                              "media": [],
                              "_id": "6718ab859a5e7251f6d67e43"
                            }
                          ],
                          "explain": {
                            "type": "",
                            "text": "",
                            "media": [],
                            "hasMath": false,
                            "math": {
                              "template": null,
                              "latex": []
                            },
                            "answerTime": 0
                          },
                          "hints": [],
                          "kind": "BLANK",
                          "theme": {
                            "fontFamily": "Quicksand",
                            "titleFontFamily": "Quicksand",
                            "fontColor": {
                              "text": "#5D2057"
                            },
                            "background": {
                              "color": "#FFFFFF",
                              "image": "",
                              "video": ""
                            },
                            "shape": {
                              "largeShapeColor": "#F2F2F2",
                              "smallShapeColor": "#9A4292"
                            }
                          },
                          "marks": {
                            "correct": 1,
                            "incorrect": 0
                          },
                          "answer": 0
                        },
                        "standards": [],
                        "topics": [],
                        "isSuperParent": false,
                        "standardIds": [],
                        "aiGenerated": false,
                        "__v": 1,
                        "teleportFrom": null,
                        "cloned": false,
                        "ver": 4,
                        "parent": null,
                        "_id": "6718aba44aa76ced51ccecb2",
                        "time": 60000,
                        "published": true,
                        "aiCreateMeta": null,
                        "type": "BLANK",
                        "createdAt": "2024-10-23T07:54:12.908Z",
                        "updated": "2024-10-23T07:54:34.675Z",
                        "cached": true
                      },
                      {
                        "structure": {
                          "settings": {
                            "hasCorrectAnswer": true,
                            "fibDataType": "string",
                            "canSubmitCustomResponse": false,
                            "doesOptionHaveMultipleTargets": false,
                            "ignoreAccentMarksForEvaluation": false,
                            "enableAccentMarks": false,
                            "questionMetadata": {
                              "specialIndices": [],
                              "answerLength": 4
                            },
                            "questionDisplayVariant": "single_blank"
                          },
                          "hasMath": false,
                          "query": {
                            "type": null,
                            "hasMath": false,
                            "math": {
                              "latex": [],
                              "template": null
                            },
                            "answerTime": -1,
                            "text": "\u003Cp\u003EThis is a question with an image\u003C/p\u003E",
                            "media": [
                              {
                                "type": "image",
                                "url": "https://media.quizizz.com/_mdserver/main/media/resource/gs/quizizz-media/quizzes/7d9dc490-dee9-4a40-9b48-c2323025f9e8-v2",
                                "meta": {
                                  "width": 0,
                                  "height": 0,
                                  "text": "",
                                  "bgColor": "",
                                  "layout": "",
                                  "videoId": "",
                                  "start": 0,
                                  "end": 0,
                                  "duration": 0,
                                  "kind": "",
                                  "embeddable": true,
                                  "title": ""
                                }
                              }
                            ]
                          },
                          "options": [
                            {
                              "type": "text",
                              "hasMath": false,
                              "math": {
                                "latex": [],
                                "template": null
                              },
                              "answerTime": 0,
                              "id": "6718ab859a5e7251f6d67e43",
                              "text": "true",
                              "media": [],
                              "_id": "6718ab859a5e7251f6d67e43"
                            }
                          ],
                          "explain": {
                            "type": "",
                            "text": "",
                            "media": [],
                            "hasMath": false,
                            "math": {
                              "template": null,
                              "latex": []
                            },
                            "answerTime": 0
                          },
                          "hints": [],
                          "kind": "BLANK",
                          "theme": {
                            "fontFamily": "Quicksand",
                            "titleFontFamily": "Quicksand",
                            "fontColor": {
                              "text": "#5D2057"
                            },
                            "background": {
                              "color": "#FFFFFF",
                              "image": "",
                              "video": ""
                            },
                            "shape": {
                              "largeShapeColor": "#F2F2F2",
                              "smallShapeColor": "#9A4292"
                            }
                          },
                          "marks": {
                            "correct": 1,
                            "incorrect": 0
                          },
                          "answer": 0
                        },
                        "standards": [],
                        "topics": [],
                        "isSuperParent": false,
                        "standardIds": [],
                        "aiGenerated": false,
                        "__v": 1,
                        "teleportFrom": null,
                        "cloned": false,
                        "ver": 4,
                        "parent": null,
                        "_id": "6718aba9bf9bde6e581976e1",
                        "time": 60000,
                        "published": true,
                        "aiCreateMeta": null,
                        "type": "BLANK",
                        "createdAt": "2024-10-23T07:54:17.145Z",
                        "updated": "2024-10-23T07:54:34.675Z",
                        "cached": true
                      }
                    ],
                    "questionDrafts": [],
                    "standards": null,
                    "courses": null,
                    "createdAt": "2024-10-23T07:53:42.817Z",
                    "updated": "2024-10-23T07:54:34.679Z",
                    "isProfane": false,
                    "whitelisted": true,
                    "slug": "Quizziz API "
                  },
                  "createdAt": "2024-09-29T11:41:19.500Z",
                  "updated": "2024-10-23T07:54:34.680Z",
                  "premiumUse": "DISABLED",
                  "hasDraftVersion": false,
                  "hasPublishedVersion": true,
                  "gameChangerBadge": null,
                  "badges": {

                  },
                  "lock": null
                },
                "draft": null
              },
              "meta": {
                "service": "q_api_all",
                "version": "43183ba_6717b68d"
              }
            }
            
        """.trimIndent(),
        JsonElement::class.java,
    )

    @Test
    fun testJsonDataLoaded() {
        Assert.assertTrue(
            "The json data must be an object",
            jsonData.isJsonObject,
        )
        Assert.assertTrue(
            "Json object should contain success property that is a boolean",
            jsonData.asJsonObject.get("success").isJsonPrimitive,
        )
    }

    @Test
    fun testDeserializedQuizFromJson() {
        val quiz = QuizJsonDeserializer().deserializeJson(jsonData)
        Assert.assertEquals(
            "Quiz remote id should be <66f93cdf2ea35985eb870319> but got <${quiz.remoteId}>",
            "66f93cdf2ea35985eb870319",
            quiz.remoteId,
        )
        Assert.assertEquals(
            "Quiz question count should be 7 but got <${quiz.supportedQuestions.size}>",
            7,
            quiz.supportedQuestions.size
        )
        Assert.assertEquals(
            "Quiz name should be <Quizziz API > but received <${quiz.name}>",
            "Quizziz API ",
            quiz.name,
        )
    }

}