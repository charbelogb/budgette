import api from './api'
import type { Account, AddAccountRequest } from '@/types/account'

export const accountService = {
  async getAccounts(): Promise<Account[]> {
    const response = await api.get<Account[]>('/accounts')
    return response.data
  },

  async addAccount(data: AddAccountRequest): Promise<Account> {
    const response = await api.post<Account>('/accounts', data)
    return response.data
  },
}
