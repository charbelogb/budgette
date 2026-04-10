export type Operator = 'MTN' | 'MOOV'
export type Country = 'BJ'

export interface Account {
  id: string
  operator: Operator
  operatorDisplayName: string
  country: Country
  phoneNumber: string
  balance: number
  currency: string
  active: boolean
}

export interface AddAccountRequest {
  operator: Operator
  country: Country
  phoneNumber: string
}
