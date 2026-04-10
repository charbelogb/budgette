import { useForm } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import { z } from 'zod'
import { useNavigate } from 'react-router-dom'
import { useState } from 'react'
import { accountService } from '@/services/accountService'

const addAccountSchema = z.object({
  operator: z.enum(['MTN', 'MOOV'], { required_error: "Sélectionnez un opérateur" }),
  phoneNumber: z.string()
    .min(8, 'Numéro trop court')
    .regex(/^\+?[0-9]+$/, 'Numéro invalide'),
})

type AddAccountForm = z.infer<typeof addAccountSchema>

const operators = [
  { value: 'MTN', label: 'MTN Mobile Money', icon: '📱', color: 'border-yellow-300 bg-yellow-50' },
  { value: 'MOOV', label: 'Moov Money', icon: '📲', color: 'border-blue-300 bg-blue-50' },
] as const

export default function AddAccountPage() {
  const navigate = useNavigate()
  const [error, setError] = useState<string | null>(null)
  const [loading, setLoading] = useState(false)

  const { register, handleSubmit, watch, setValue, formState: { errors } } = useForm<AddAccountForm>({
    resolver: zodResolver(addAccountSchema),
  })

  const selectedOperator = watch('operator')

  const onSubmit = async (data: AddAccountForm) => {
    setLoading(true)
    setError(null)
    try {
      await accountService.addAccount({
        operator: data.operator,
        country: 'BJ',
        phoneNumber: data.phoneNumber,
      })
      navigate('/accounts')
    } catch {
      setError('Impossible d\'ajouter le compte. Vérifiez le numéro et réessayez.')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="max-w-lg">
      <div className="mb-6">
        <h1 className="text-2xl font-bold text-gray-900">Ajouter un compte</h1>
        <p className="text-gray-500 text-sm mt-1">
          Connectez votre compte Mobile Money pour suivre vos finances
        </p>
      </div>

      <div className="bg-white rounded-2xl border border-gray-200 p-8">
        <form onSubmit={handleSubmit(onSubmit)} className="space-y-6">
          {error && (
            <div className="p-3 bg-red-50 border border-red-200 rounded-lg text-sm text-red-700">
              {error}
            </div>
          )}

          {/* Operator selection */}
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-3">
              Opérateur Mobile Money
            </label>
            <div className="grid grid-cols-2 gap-3">
              {operators.map((op) => (
                <button
                  key={op.value}
                  type="button"
                  onClick={() => setValue('operator', op.value)}
                  className={`flex flex-col items-center gap-2 p-4 rounded-xl border-2 transition-all ${
                    selectedOperator === op.value
                      ? op.color + ' border-opacity-100'
                      : 'border-gray-200 bg-white hover:border-gray-300'
                  }`}
                >
                  <span className="text-2xl">{op.icon}</span>
                  <span className="text-sm font-medium text-gray-900">{op.label}</span>
                </button>
              ))}
            </div>
            {errors.operator && (
              <p className="text-xs text-red-600 mt-1">{errors.operator.message}</p>
            )}
          </div>

          {/* Country (fixed to BJ) */}
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Pays</label>
            <div className="flex items-center gap-2 px-3 py-2.5 bg-gray-50 border border-gray-200 rounded-lg text-sm text-gray-600">
              🇧🇯 Bénin (BJ)
            </div>
          </div>

          {/* Phone number */}
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">
              Numéro de téléphone
            </label>
            <input
              type="tel"
              {...register('phoneNumber')}
              className="w-full px-3 py-2.5 border border-gray-300 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-amber-500 focus:border-transparent"
              placeholder="+22961234567"
            />
            {errors.phoneNumber && (
              <p className="text-xs text-red-600 mt-1">{errors.phoneNumber.message}</p>
            )}
          </div>

          <div className="flex gap-3">
            <button
              type="button"
              onClick={() => navigate('/accounts')}
              className="flex-1 py-3 bg-gray-100 text-gray-700 font-medium rounded-lg hover:bg-gray-200 transition-colors"
            >
              Annuler
            </button>
            <button
              type="submit"
              disabled={loading}
              className="flex-1 py-3 bg-amber-500 text-white font-medium rounded-lg hover:bg-amber-600 transition-colors disabled:opacity-50"
            >
              {loading ? 'Ajout en cours...' : 'Ajouter le compte'}
            </button>
          </div>
        </form>
      </div>
    </div>
  )
}
