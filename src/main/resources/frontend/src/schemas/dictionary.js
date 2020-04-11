export default {
  schemas: {
    composer: {
      en: 'Composer',
      ua: 'Композитор',
      properties: {
        composerName: {
          ua: "Ім'я",
          en: "Name"
        },
        composerCountryCode: {
          ua: 'Країна',
          en: 'Country'
        },
        activityStart: {
          ua: 'Початок діяльності',
          en: 'Activity start'
        },
        activityEnd: {
          ua: 'Кінець активності',
          en: 'Activity end'
        },
        trackIds: {
          ua: 'Композиції',
          en: 'Tracks'
        }
      }
    },
    track: {
      en: 'Track',
      ua: 'Композиція',
      properties: {
        trackCatalogNum: {
          ua: 'Номер у каталозі',
          en: 'Catalog number'
        },
        trackName: {
          ua: 'Назва',
          en: 'Name'
        },
        duration: {
          ua: 'Тривалість',
          en: 'Duration'
        },
        composerIds: {
          ua: 'Композитори',
          en: 'Composers'
        },
        languages: {
          ua: 'Мови',
          en: 'Languages'
        },
        artistIds: {
          ua: 'Виконавці',
          en: 'Artists'
        },
        bandIds: {
          ua: 'Групи',
          en: 'Bands'
        },
        featuringArtistIds: {
          ua: 'Другорядні виконавці',
          en: 'Featuring artists'
        },
        featuringBandIds: {
          ua: 'Другорядні групи',
          en: 'Featuring bands'
        },
        artistBindings: {
          ua: 'Зв\'язки з виконавцями',
          en: 'Artists bindings'
        },
        bandBindings: {
          ua: 'Зв\'язки з групами',
          en: 'Bands bindings'
        },
        albumIds: {
          ua: 'Альбоми',
          en: 'Albums'
        }
      }
    },
    artist: {
      en: 'Artist',
      ua: 'Виконавець',
      properties: {
        artistAlias: {
          ua: 'Псевдонім',
          en: 'Alias'
        },
        isArtistActive: {
          ua: 'Активний',
          en: 'Active'
        },
        artistCountryCode: {
          ua: 'Країна',
          en: 'Country'
        },
        artistName: {
          ua: 'Iм\'я',
          en: 'Name'
        },
        artistBirthDate: {
          ua: 'Дата народження',
          en: 'Birth date'
        },
        artistDeathDate: {
          ua: 'Дата смерті',
          en: 'Death date'
        },
        currentBandAliases: {
          ua: 'Поточні групи',
          en: 'Current bands'
        },
        previousBandAliases: {
          ua: 'Колишні групи',
          en: 'Former bands'
        },
        trackCatalogNums: {
          ua: 'Треки',
          en: 'Tracks'
        },
        featuringTrackCatalogNums: {
          ua: 'Другорядні треки',
          en: 'Featuring tracks'
        },
        artistBindings: {
          ua: 'Зв\'язки з треками',
          en: 'Track bindings'
        },
        participations: {
          ua: 'Зв\'язки з групами',
          en: 'Bands bindings'
        }
      }
    },
    artist2track: {
      en: 'Artist - track connection',
      ua: 'Зв\'язок трек - виконавець',
      properties: {
        trackCatalogNum: {
          en: 'Track',
          ua: 'Трек'
        },
        artistAlias: {
          en: 'Artist',
          ua: 'Виконавець'
        },
        isFeaturing: {
          en: 'Featuring',
          ua: 'Другорядний'
        }
      }
    },
    band2track: {
      en: 'Artist - Track connection',
      ua: 'Зв\'язок група - трек',
      properties: {
        trackCatalogNum: {
          en: 'Track',
          ua: 'Трек'
        },
        bandAlias: {
          en: 'Band',
          ua: 'Група'
        },
        isFeaturing: {
          en: 'Featuring',
          ua: 'Другорядний'
        }
      }
    },
    band: {
      en: 'Band',
      ua: 'Група',
      properties: {
        bandAlias: {
          ua: 'Назва',
          en: 'Alias'
        },
        isBandActive: {
          ua: 'Активна',
          en: 'Active'
        },
        bandCountryCode: {
          ua: 'Країна',
          en: 'Country'
        },
        startYear: {
          ua: 'Дата заснування',
          en: 'Start date'
        },
        endYear: {
          ua: 'Дата розпаду',
          en: 'End date'
        },
        currentArtistAliases: {
          ua: 'Поточні виконавці',
          en: 'Current artists'
        },
        previousArtistAliases: {
          ua: 'Колишні виконавці',
          en: 'Previous artists'
        },
        trackCatalogNums: {
          ua: 'Треки',
          en: 'Tracks'
        },
        featuringTracksCatalogNums: {
          ua: 'Другорядні треки',
          en: 'Featuring tracks'
        },
        participations: {
          ua: 'Зв\'язки з учасниками',
          en: 'Artists bindings'
        }
      }
    },
    participation: {
      en: 'Band participation',
      ua: 'Участь у групі',
      properties: {
        participationArtistAlias: {
          en: 'Artist',
          ua: 'Виконавець'
        },
        participationBandAlias: {
          en: 'Band',
          ua: 'Група'
        },
        joinDate: {
          en: 'Join date',
          ua: 'Дата вступу'
        },
        exitDate: {
          en: 'Exit date',
          ua: 'Дата виходу'
        }
      }
    },
    album: {
      en: 'Album',
      ua: 'Альбом',
      properties: {
        albumCatalogNum: {
          en: 'Catalog #',
          ua: 'Номер каталогу'
        },
        releaseYear: {
          en: 'Release year',
          ua: 'Рік випуску'
        },
        albumGenres: {
          en: 'Genres',
          ua: 'Жанри'
        },
        albumName: {
          en: 'Name',
          ua: 'Назва'
        },
        variousArtists: {
          en: 'Compilation',
          ua: 'Компіляція'
        },
        trackCatalogNums: {
          en: 'Tracks',
          ua: 'Треки'
        },
        rock: {
          en: 'Rock',
          ua: 'Рок'
        },
        pop: {
          en: 'Pop',
          ua: 'Поп'
        },
        classic: {
          en: 'Classical',
          ua: 'Класична'
        },
        indie: {
          en: 'Indie',
          ua: 'Інді'
        },
        electronic: {
          en: 'Electronic',
          ua: 'Електроніка'
        },
        soul: {
          en: 'Soul',
          ua: 'Соул'
        },
        "hip hop": {
          en: 'Hip-Hop',
          ua: 'Хіп-хоп'
        },
        reggae: {
          en: 'Reggae',
          ua: 'Реггі'
        },
        funk: {
          en: 'Funk',
          ua: 'Фанк'
        },
        releaseBarcodes: {
          en: 'Releases',
          ua: 'Релізи'
        }
      }
    },
    release: {
      en: 'Release',
      ua: 'Реліз',
      properties: {
        releaseBarcode: {
          en: 'Barcode #',
          ua: 'Штрих-код'
        },
        albumCatalogNum: {
          en: 'Album',
          ua: 'Альбом'
        },
        releaseCountryCode: {
          en: 'Country',
          ua: 'Країна'
        },
        releaseDate: {
          en: 'Release date',
          ua: 'Дата випуску'
        },
        recordSize: {
          en: 'Record size',
          ua: 'Розмір платівки'
        },
        recordSpeed: {
          en: 'Record speed',
          ua: 'Швидкість програвання'
        },
        copiesCount: {
          en: 'Copies count',
          ua: 'Наклад'
        },
        isRepress: {
          en: 'Repress',
          ua: 'Перевидання'
        },
        label: {
          en: 'Label',
          ua: 'Лейбл'
        }
      }
    },
    record: {
      en: 'Record',
      ua: 'Платівка',
      properties: {
        recordBarcode: {
          en: 'Internal barcode',
          ua: 'Штрих-код магазину'
        },
        releaseBarcodeFk: {
          en: 'Release',
          ua: 'Реліз'
        },
        purchaseDate: {
          en: 'Purchase date',
          ua: 'Дата закупівлі'
        },
        purchasePrice: {
          en: 'Purchase price',
          ua: 'Ціна закупівлі'
        },
        sellPrice: {
          en: 'Sell price',
          ua: 'Ціна продажу'
        },
        available: {
          en: 'Available',
          ua: 'В наявності'
        },
        recordState: {
          en: 'Condition',
          ua: 'Стан'
        },
        stateCheckDate: {
          en: 'State check date',
          ua: 'Дата перевірки стану'
        },
        supplierEdrpou: {
          en: 'Supplier',
          ua: 'Постачальник'
        }
      }
    },
    salesman: {
      en: 'Salesman',
      ua: 'Продавець',
      properties: {
        tabNum: {
          en: 'ID',
          ua: 'Табельний #'
        },
        salesmanName: {
          en: 'Name',
          ua: 'Ім\'я'
        },
        passportNum: {
          en: 'Passport #',
          ua: 'Номер паспорту'
        },
        addressCity: {
          en: 'City',
          ua: 'Місто'
        },
        addressStr: {
          en: 'Street',
          ua: 'Вулиця'
        },
        addressHome: {
          en: 'Home',
          ua: 'Будинок'
        },
        addressApt: {
          en: 'Apartment',
          ua: 'Квартира'
        },
        salesmanPhoneNum: {
          en: 'Phone',
          ua: 'Телефон'
        },
        worksFrom: {
          en: 'Works from',
          ua: 'Працює з'
        },
        worksTo: {
          en: 'Works to',
          ua: 'Працює до'
        },
        salary: {
          en: 'Salary',
          ua: 'З/п'
        },
        checks: {
          en: 'Checks',
          ua: 'Чеки'
        }
      }
    },
    customer: {
      en: 'Customer',
      ua: 'Покупець',
      properties: {
        customerNum: {
          en: 'Customer #',
          ua: 'Номер'
        },
        customerName: {
          en: 'Name',
          ua: 'Ім\'я'
        },
        customerEmail: {
          en: 'Email',
          ua: 'Email'
        },
        customerDiscount: {
          en: 'Discount',
          ua: 'Знижка'
        },
        checksNum: {
          en: 'Checks',
          ua: 'Чеки'
        },
        customerPhoneNumbers: {
          en: 'Phones',
          ua: 'Телефони'
        }
      }
    },
    check: {
      en: 'Check',
      ua: 'Чек',
      properties: {
        checkNum: {
          en: 'Check #',
          ua: 'Номер'
        },
        dateTime: {
          en: 'Date/Time',
          ua: 'Дата/Час'
        },
        customerNum: {
          en: 'Customer',
          ua: 'Покупець'
        },
        salesmanTabNum: {
          en: 'Salesman',
          ua: 'Продавець'
        },
        overallSum: {
          en: 'Sum',
          ua: 'Сума'
        },
        checkDiscount: {
          en: 'Discount',
          ua: 'Знижка'
        },
        sumWithDiscount: {
          en: 'Sum with discount',
          ua: 'Сума зі знижкою'
        },
        productBarcodes: {
          en: 'Records',
          ua: 'Платівки'
        }
      }
    },
    supplier: {
      en: 'Supplier',
      ua: 'Постачальник',
      properties: {
        edrpou: {
          en: 'EDRPOU',
          ua: 'ЄДРПОУ'
        },
        supplierAddress: {
          en: 'Address',
          ua: 'Адреса'
        },
        phoneNumber: {
          en: 'Phone',
          ua: 'Телефон'
        },
        records: {
          en: 'Records',
          ua: 'Платівки'
        }
      }
    }
  }
}