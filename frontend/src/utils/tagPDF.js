import pdfMake from 'pdfmake/build/pdfmake'
import pdfFonts from 'pdfmake/build/vfs_fonts'

import { REV_ROLES_REPRESENTATION, REPRESENTATION } from '../utils/roles'

const pageConfig = () => {

  return {
    pageSize: 'A4',
    pageOrientation: 'portrait',
    pageMargins: [40, 60, 40, 60]
  }
}

const content = ({ name, role, id, documentType, documentValue, services, shotDate, responsible, credential }, offset = 0) => {
  const EMPTY_LINE = '                      '

  const roleSpecificInfo = (role) => {
    switch (role) {
      case REPRESENTATION.USER:
        return ["serviços: " + services.join(' | ')]

      case REPRESENTATION.EMPLOYEE:
        return ["Nº USP: " + credential.nusp]
    }
  }

  const offsetLines = () => {
    const base = []

    for (let i = 0; i < offset / 2; i++)
      base[i] = [[
        EMPTY_LINE,
        EMPTY_LINE,
        EMPTY_LINE,
        EMPTY_LINE,
        EMPTY_LINE,
        EMPTY_LINE
      ], [
        EMPTY_LINE,
        EMPTY_LINE,
        EMPTY_LINE,
        EMPTY_LINE,
        EMPTY_LINE,
        EMPTY_LINE
      ]]

    return base
  }

  const offsetCol = () => {
    return offset % 2 === 1
      ? [[]]
      : []
  }

  const card = () => {
    return [
      { text: `${name} | ID-CEIP: ${id}`, bold: true },
      `${documentType}: ${documentValue}`,
      `Últ. dose: ${shotDate}`,
      EMPTY_LINE,
      REV_ROLES_REPRESENTATION[role],
      ...roleSpecificInfo(role)
    ]
  }

  return {
    content: [
      {
        table: {
          headerRows: 1,
          widths: ["50%", "50%"],

          body: [
            ...offsetLines(),
            [
              ...offsetCol(),
              card(),
            ]
          ]
        }
      }
    ]
  }
}

const tagPDF = (person) => {
  pdfMake.vfs = pdfFonts.pdfMake.vfs

  const doc = {
    ...pageConfig(),

    ...content(person)
  }

  pdfMake.createPdf(doc).download()
}

export default tagPDF